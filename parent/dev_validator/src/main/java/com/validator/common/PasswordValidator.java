package com.validator.common;

import com.validator.entity.User;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordEqual, User> {
    @Override
    public boolean isValid(User value, ConstraintValidatorContext context) {
        if(StringUtils.isEmpty(value.getPassword())){
            return false;
        }
        if(value.getPassword().equals("123456")){
            return false;
        }
        return true;
    }
}
