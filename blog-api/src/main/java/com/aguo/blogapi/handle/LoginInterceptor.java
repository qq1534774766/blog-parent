package com.aguo.blogapi.handle;

import com.aguo.blogapi.enums.ErrorCode;
import com.aguo.blogapi.pojo.SysUser;
import com.aguo.blogapi.service.LoginService;
import com.aguo.blogapi.untils.UserThreadLocal;
import com.aguo.blogapi.vo.AGuoResult;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @Author: aguo
 * @DateTime: 2022/4/27 14:43
 * @Description: TODO
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
//        1. 校验token
//        token无效时，拦截
        Map<String, Object> map = loginService.verifyToken(token);
        if (!(boolean)map.get("success")){
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(AGuoResult.failed((Integer)map.get("code"), (String)map.get("msg"))));
            return false;
        }
//        token有效,将SysUser当前对象放到threadlocal中，方便线程获取当前用户的数据
        UserThreadLocal.put((SysUser) map.get("sysUser"));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
