package com.drools.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.drools.common.inters.BaseReq;
import com.drools.mapper.RuleInterfaceLogMapper;
import com.drools.model.RuleInterfaceLog;
import com.drools.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Aspect
@Configuration
public class InterfaceLogAspect {

    @Autowired
    RuleInterfaceLogMapper ruleInterfaceLogMapper;

    //创建Pointcut表示式，表示所有controller请求
    @Pointcut("execution(* com.drools.rest.ThirdApiController.*(..))")
    private void controllerAspect() {
    }// 请求method前打印内容

    @Around(value = "controllerAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //通过uuid关联请求参数和返回参数
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        RuleInterfaceLog ruleInterfaceLog = new RuleInterfaceLog();
        methodBefore(pjp, uuid, ruleInterfaceLog);

        HttpServletRequest request = RequestUtils.getHttpServletRequest();
        ruleInterfaceLog.setIp(RequestUtils.getIp(request));
        Object proceed = null;
        try {
            Date start = new Date();

             proceed = pjp.proceed();

            Date end = new Date();
            long useTime = end.getTime() - start.getTime();
            ruleInterfaceLog.setBeginTime(start);
            ruleInterfaceLog.setEndTime(end);
            ruleInterfaceLog.setUseTime(useTime);

            methodAfter(proceed, uuid, ruleInterfaceLog);
        } catch (Exception e) {
            log.error("[{}]Response异常内容:{}", uuid, e);
            throw e;
        }
        return proceed;
    }

    private void methodAfter(Object proceed, String uuid, RuleInterfaceLog ruleInterfaceLog) {
        log.info("[{}]接口返回[{}]",uuid,JSON.toJSONString(proceed));
        ruleInterfaceLog.setRemark(uuid);
        ruleInterfaceLog.setRes(JSON.toJSONString(proceed));
        ruleInterfaceLogMapper.insertSelective(ruleInterfaceLog);
    }


    public void methodBefore(JoinPoint joinPoint, String uuid, RuleInterfaceLog ruleInterfaceLog) {
        // 打印请求内容
        try {
            // 下面两个数组中，参数值和参数名的个数和位置是一一对应的。
            Object[] objs = joinPoint.getArgs();
            String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames(); // 参数名
            Object obj = null;
            for (int i = 0; i < objs.length; i++) {
                if ((objs[i] instanceof BaseReq)) {
                    obj = objs[i];
                }
            }

            if (obj != null) {
                log.info("\n接口请求[{}]方法:{}\n参数:{}", uuid, joinPoint.getSignature(), JSONObject.toJSONString(obj));
                String jsonStr = JSON.toJSONString(obj);
                JSONObject jsonObject = JSONObject.parseObject(jsonStr);
                JSONObject baseInfo = jsonObject.getJSONObject("body").getJSONObject("baseInfo");
                JSONObject head = jsonObject.getJSONObject("head");
                String clmnum = baseInfo.getString("clmnum");
                String visitcode = head.getString("visitCode");
                String transactionNo = head.getString("transactionNo");
                ruleInterfaceLog.setClmnum(clmnum);
                ruleInterfaceLog.setInterfaceIdentify(visitcode);
                ruleInterfaceLog.setReq(JSON.toJSONString(obj));
                ruleInterfaceLog.setBatchNo(transactionNo);
            }
        } catch (Exception e) {
            log.error("[{}]AOP methodBefore:", uuid, e);
        }
    }


    @AfterThrowing(value = "controllerAspect()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();

        Method method = ms.getMethod();
    }
}
