

package com.shiro.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Redis切面处理类
 *
 */
@Slf4j
@Aspect
@Configuration
public class RedisAspect {

    @Before("execution(* com.shiro.rest.UserController.edit())")
    public void permissionCheck(JoinPoint point) {
        log.info("@Before：模拟权限检查...");
        log.info("@Before：目标方法为：" +
                point.getSignature().getDeclaringTypeName() +
                "." + point.getSignature().getName());
        log.info("@Before：参数为：" + Arrays.toString(point.getArgs()));
        log.info("@Before：被织入的目标对象为：" + point.getTarget());
    }

    @After("execution(* com.shiro.rest.UserController.edit())")
    public void releaseResource(JoinPoint point) {
        log.info("@After：模拟释放资源...");
        log.info("@After：目标方法为：" +
                point.getSignature().getDeclaringTypeName() +
                "." + point.getSignature().getName());
        log.info("@After：参数为：" + Arrays.toString(point.getArgs()));
        log.info("@After：被织入的目标对象为：" + point.getTarget());
    }

    @AfterReturning(pointcut="execution(* com.shiro.rest.UserController.edit())",
            returning="returnValue")
    public void log(JoinPoint point, Object returnValue) {
        log.info("@AfterReturning：模拟日志记录功能...");
        log.info("@AfterReturning：目标方法为：" +
                point.getSignature().getDeclaringTypeName() +
                "." + point.getSignature().getName());
        log.info("@AfterReturning：参数为：" +
                Arrays.toString(point.getArgs()));
        log.info("@AfterReturning：返回值为：" + returnValue);
        log.info("@AfterReturning：被织入的目标对象为：" + point.getTarget());
    }

    @Around("execution(* com.shiro.rest.UserController.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        log.info("Around begin...");
        result = point.proceed();
        log.info("Around end...");
        return result;
    }

    /**
     * 在所有标记了@SMSAndMailSender的方法中切入
     * @param joinPoint
     * @param result
     */
//    @AfterReturning(value="@annotation(com.trip.demo.SMSAndMailSender)", returning="result")//有注解标记的方法，执行该后置返回
//    public void afterReturning(JoinPoint joinPoint , Object result//注解标注的方法返回值) {
//        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
//    Method method = ms.getMethod();
//    boolean active = method.getAnnotation(SMSAndMailSender.class).isActive();
//        if (!active) {
//        return;
//    }
//    String smsContent = method.getAnnotation(SMSAndMailSender.class).smsContent();
//    String mailContent = method.getAnnotation(SMSAndMailSender.class).mailContent();
//    String subject = method.getAnnotation(SMSAndMailSender.class).subject();


    /**
     * 在抛出异常时使用
     * @param joinPoint
     * @param ex
     */
//    @AfterThrowing(value="@annotation(com.trip.order.monitor.SMSAndMailSender)",throwing = "ex")
//    public void afterThrowing(JoinPoint joinPoint, Throwable ex//注解标注的方法抛出的异常) {
//        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
//    Method method = ms.getMethod();
//    String subject = method.getAnnotation(SMSAndMailSender.class).subject();
//
//     }

}
