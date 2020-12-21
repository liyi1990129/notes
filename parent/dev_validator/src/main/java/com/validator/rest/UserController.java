package com.validator.rest;


import com.validator.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @PostMapping(value = "test")
    public String test(@Validated User user){

        return "sss";
    }
    @PostMapping(value = "test2")
    public String test(){
        logger.info("- 数据库日志info");
        logger.error("- 数据库日志error");
        logger.info("一条不带‘-’的日志，看会不会记录如数据库");
        return "sss";
    }
    @PostMapping(value = "test1")
    public String test1(@Valid User user, BindingResult result){
        if(result.hasErrors()){
            StringBuffer sb = new StringBuffer("");
            result.getAllErrors().forEach(error-> sb.append(error.getDefaultMessage()));
            return sb.toString();
        }
        return "sss";
    }

}