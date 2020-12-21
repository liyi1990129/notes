//package com.ly.common;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.alibaba.fastjson.JSON;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authz.AuthorizationException;
//import org.apache.shiro.authz.UnauthenticatedException;
//import org.apache.shiro.authz.UnauthorizedException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//
//public abstract class BaseController {
//    /**
//     * 登录认证异常
//     *
//     * @param request
//     * @param response
//     * @return
//     */
//    @ExceptionHandler({ UnauthenticatedException.class, AuthenticationException.class })
//    public String authenticationException(HttpServletRequest request, HttpServletResponse response) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("status", "-1000");
//        map.put("message", "未登录");
//        writeJson(map, response);
//        return null;
//    }
//
//    /**
//     * 权限异常
//     *
//     * @param request
//     * @param response
//     * @return
//     */
//    @ExceptionHandler({ UnauthorizedException.class, AuthorizationException.class })
//    public String authorizationException(HttpServletRequest request, HttpServletResponse response) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("status", "-1001");
//        map.put("message", "无权限");
//        writeJson(map, response);
//        return null;
//    }
//
//    private void writeJson(Map<String, Object> map, HttpServletResponse response) {
//        PrintWriter out = null;
//        try {
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json; charset=utf-8");
//            out = response.getWriter();
//
//            out.write(JSON.toJSONString(map));
//        } catch (IOException e) {
//        } finally {
//            if (out != null) {
//                out.close();
//            }
//        }
//    }
//}
