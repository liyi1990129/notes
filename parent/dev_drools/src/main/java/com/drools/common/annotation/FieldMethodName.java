package com.drools.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface FieldMethodName {
    String name() default ""; // 方法名称
    String entityCls() default ""; // 归属实体类包名
    String params() default "";//参数 name=int 由参数名 = 参数类型 组成 多个逗号隔开
    String paramsDesc() default "";//参数说明，与参数顺序对应 多个逗号隔开
    String methodType() default "";//方法类型 0-无参 1-有参
}
