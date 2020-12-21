package com.shiro.rest;


import com.shiro.entity.User;
import com.shiro.service.UserService;
import com.shiro.vo.HttpResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/getUser")
    public User getUser(){
        return userService.getById(1);
    }


    @RequiresPermissions("sys:role:list")
    @PostMapping("/findAllUser")
    public List<User> findAllUser(){
        return userService.findAllUser();
    }

    @RequiresPermissions("sys:user:add")
    @GetMapping(value="/findAll")
    public HttpResult findAll() {
        return HttpResult.ok("the findAll service is called success.");
    }

    @RequiresRoles("admin")
    @GetMapping(value="/edit")
    public HttpResult edit() {
        return HttpResult.ok("the edit service is called success.");
    }

    @RequiresRoles("admin1")
    @GetMapping(value="/delete")
    public HttpResult delete() {
        return HttpResult.ok("the delete service is called success.");
    }

}