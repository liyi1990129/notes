package com.drools.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface FieldName {
    String name() default ""; // 属性名称
    String type() default ""; //属性类型  DroolsConstants.FieldType
    String enumCode() default ""; //枚举字段 对应字典表的 sdmCode
    String listCls() default ""; // 对象包名
    String remark() default ""; // 备注
}
