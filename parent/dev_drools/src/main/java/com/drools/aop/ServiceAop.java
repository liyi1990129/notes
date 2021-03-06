package com.drools.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 描述：
 */
@Aspect
@Component
public class ServiceAop {
    private static Logger logger = LoggerFactory.getLogger(ServiceAop.class);
    private ThreadLocal<Long> timeLocal = new ThreadLocal<>();

    //com.drools.service.impl 包(包括子目录)下所有类
    @Pointcut("execution(public * com.drools.service.impl..*.*(..))")
    public void webRequestLog() {
    }

    @Before("webRequestLog()")
    public void doBefore(JoinPoint joinPoint) {
        timeLocal.set(System.currentTimeMillis());
    }

    @AfterReturning(returning = "result", pointcut = "webRequestLog()")
    public void doAfterReturning(Object result) {
        long startTime = timeLocal.get();
        logger.info("花费的时间为:" + (System.currentTimeMillis() - startTime) + "毫秒");
    }

    @Around("webRequestLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object o;
        o = pjp.proceed();
        return o;
    }
}
