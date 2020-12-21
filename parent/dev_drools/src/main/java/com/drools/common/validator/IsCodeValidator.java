package com.drools.common.validator;

import com.alibaba.druid.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsCodeValidator implements ConstraintValidator<IsCode, String> {

    private boolean required = false;
    private String code = "";

    //工具方法，判断是否匹配
    public  boolean isCode(String v) {
        if (StringUtils.isEmpty(v)) {
            return false;
        }
        return code.equals(v);
    }

    @Override
    public void initialize(IsCode constraintAnnotation) {
        required = constraintAnnotation.isRequired();
        code = constraintAnnotation.code();
    }

    @Override
    public boolean isValid(String v, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return isCode(v);
        } else {
            if (StringUtils.isEmpty(v)) {
                return true;
            } else {
                return isCode(v);
            }
        }
    }

}
