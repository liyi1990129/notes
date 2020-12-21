package com.drools.service.impl;

import com.alibaba.fastjson.JSON;
import com.drools.biz.DroolsBiz;
import com.drools.biz.publish.PublishRuleSceneBiz;
import com.drools.common.DroolsConstants;
import com.drools.entity.clm.Claim;
import com.drools.mapper.RuleSceneLogMapper;
import com.drools.model.RuleSceneLog;
import com.drools.model.fact.RuleExecutionObject;
import com.drools.model.fact.RuleExecutionResult;
import com.drools.model.publish.PublishRuleSceneInfo;
import com.drools.service.DroolsActionService;
import com.drools.service.ThirdApiService;
import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Date;

@Slf4j
@Service
public class ThirdApiServiceImpl implements ThirdApiService {

    @Autowired
    DroolsActionService droolsActionService;
    @Autowired
    PublishRuleSceneBiz publishRuleSceneBiz;
    @Autowired
    DroolsBiz droolsBiz;
    @Autowired
    private RuleSceneLogMapper ruleSceneLogMapper;

    /* *
     * 根据场景执行规则 
     * @author ly
     * @modifyTime 2020/11/25 13:06:00
     */
    public RuleExecutionObject excute(RuleExecutionObject object,String secne){
        log.info("执行场景[{}]规则开始,入参:{}", secne,JSON.toJSONString(object));
//        String str = droolsBiz.getTemplate(secne);
//        RuleExecutionObject resultData = droolsBiz.excute(object,secne,str);
        PublishRuleSceneInfo info = this.publishRuleSceneBiz.getUpdatedInfoBySecne(secne);
        String template = droolsActionService.getTemplate(info.getSceneIdentify(), info.getPublishVersion());
        RuleExecutionObject resultData = droolsActionService.excute(object, info.getSceneIdentify(), info.getPublishVersion(), template);
        log.info("执行场景[{}]规则完毕,返回:{}", secne,JSON.toJSONString(resultData));
        return resultData;
    }

    @Override
    public RuleExecutionObject excute(RuleExecutionObject object, String scene, String interfaceIdentity,String batchCode,String clmnum) {
        log.info("执行场景[{}]规则开始,入参:{}", scene,JSON.toJSONString(object));
        Date beginTime = new Date();

        PublishRuleSceneInfo info = this.publishRuleSceneBiz.getUpdatedInfoBySecne(scene);
        String template = droolsActionService.getTemplate(info.getSceneIdentify(), info.getPublishVersion());
        RuleExecutionObject resultData = droolsActionService.excute(object, info.getSceneIdentify(), info.getPublishVersion(), template);
        log.info("执行场景[{}]规则完毕,返回:{}", scene,JSON.toJSONString(resultData));
        Date endTime = new Date();


        //保存日志
        RuleSceneLog ruleSceneLog = new RuleSceneLog();
        ruleSceneLog.setClmnum(clmnum);
        ruleSceneLog.setInterfaceIdentify(interfaceIdentity);
        ruleSceneLog.setSceneIdentify(scene);
        ruleSceneLog.setBeginTime(beginTime);
        ruleSceneLog.setEndTime(endTime);
        ruleSceneLog.setUseTime(endTime.getTime()-beginTime.getTime());

        if(!resultData.getGlobalMap().isEmpty()){
            RuleExecutionResult ruleExecutionResult = (RuleExecutionResult) resultData.getGlobalMap().get("_result");
            if(!ruleExecutionResult.getMap().isEmpty()){
                ruleSceneLog.setResult(JSON.toJSONString(ruleExecutionResult));
                ruleSceneLog.setResultStatus(DroolsConstants.ResultStatus.PASS);
            }else{
                ruleSceneLog.setResultStatus(DroolsConstants.ResultStatus.REFUSE);
            }
        }else{
            ruleSceneLog.setResultStatus(DroolsConstants.ResultStatus.REFUSE);
        }

        ruleSceneLog.setRemark("");
        ruleSceneLog.setBatchNo(batchCode);

        this.ruleSceneLogMapper.insertSelective(ruleSceneLog);

        return resultData;
    }

    /* *
     * 根据java代码执行规则
     * @author ly
     * @modifyTime 2020/11/25 13:07:00
     */
    public RuleExecutionObject excute(Claim claim, String javaStr,String clmnum,String batchNo) throws Exception{
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        log.info(javaStr);
        Class<?> clazz = groovyClassLoader.parseClass(javaStr);
        Object obj = clazz.newInstance();
//        Method method = clazz.getDeclaredMethod("testGroovy");
        Method method = clazz.getMethod("testGroovy",Claim.class,String.class,String.class);
        Object val = method.invoke(obj,claim,clmnum,batchNo);

//        Object val =  method.getDefaultValue();
        RuleExecutionObject ruleExecutionObject = (RuleExecutionObject) val;
        return ruleExecutionObject;
    }
}
