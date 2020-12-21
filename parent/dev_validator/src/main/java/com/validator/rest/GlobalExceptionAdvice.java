package com.validator.rest;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionAdvice {


    /**
     *  方法不需要添加 BindingResult result
     * @Author ly
     * @Date  2020/12/11 10:49
     * @Param
     * @return
     **/
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String handleBeanValidation(HttpServletRequest reg, BindException e){
        String reqUrl = reg.getRequestURI();
        String method = reg.getMethod();

        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = formatAllErrorMessages(errors);
        Map<String,Object> map = new HashMap<>();
        map.put("code",10001);
        map.put("msg",message);
        map.put("method",method+ " " + reqUrl);
        return JSON.toJSONString(map);
    }


    // 拼接报错信息
    private String formatAllErrorMessages(List<ObjectError> errors){
        StringBuffer errorMsgs = new StringBuffer();
        errors.forEach(error -> errorMsgs.append(error.getDefaultMessage()).append(";"));
        return errorMsgs.toString();
    }
}
