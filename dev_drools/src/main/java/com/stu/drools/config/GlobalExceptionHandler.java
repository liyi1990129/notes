package com.stu.drools.config;

import com.alibaba.fastjson.JSONObject;
import com.stu.drools.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数合法性校验异常
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String validationBodyException(MethodArgumentNotValidException exception){

        StringBuffer buffer = new StringBuffer();

        BindingResult result  = exception.getBindingResult();
        if (result.hasErrors()) {

            List<ObjectError> errors = result.getAllErrors();

            errors.forEach(p ->{

                FieldError fieldError = (FieldError) p;
                log.error("Data check failure : object{"+fieldError.getObjectName()+"},field{"+fieldError.getField()+
                    "},errorMessage{"+fieldError.getDefaultMessage()+"}");
                buffer.append(fieldError.getDefaultMessage()).append(",");
            });
        }
        BaseResponse response = new BaseResponse();
//        response.getResultMsg()buffer.toString().substring(0, buffer.toString().length()-1)
        return JSONObject.toJSONString(response);
    }

}
