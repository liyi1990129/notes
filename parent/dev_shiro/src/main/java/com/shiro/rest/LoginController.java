package com.shiro.rest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.shiro.config.auth.TokenGenerator;
import com.shiro.vo.HttpResult;
import com.shiro.vo.LoginBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 登录控制器
 * @author Louis
 * @date Jun 29, 2019
 */
@Slf4j
@RestController
public class LoginController {


    /**
     * 登录接口
     */
    @PostMapping(value = "/login")
    public Map<String, Object> login(@RequestBody LoginBean loginBean, HttpServletRequest request) throws IOException {

        String token = TokenGenerator.generateValue();
//        //用户认证信息
//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
//                loginBean.getUsername(),
//                loginBean.getPassword()
//        );
//        try {
//            //进行验证，这里可以捕获异常，然后返回对应信息
//            subject.login(usernamePasswordToken);
//        } catch (UnknownAccountException e) {
//            log.error("用户名不存在！", e);
//            return "用户名不存在！";
//        } catch (AuthenticationException e) {
//            log.error("账号或密码错误！", e);
//            return "账号或密码错误！";
//        } catch (AuthorizationException e) {
//            log.error("没有权限！", e);
//            return "没有权限";
//        }
        Map<String, Object> map = new HashMap<>();
        map.put("token",token);
        return map;
    }

}