package com.drools.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// 我们可以直接拷贝系统内的注解如@Min，复制到我们新的注解中，然后根据需要修改。
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
//注解的实现类。
@Constraint(validatedBy = {IsCodeValidator.class})
public @interface IsCode {
    //校验错误的默认信息
    String message() default "接口编码不正确";
    String code() default "";

    //是否强制校验
    boolean isRequired() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
