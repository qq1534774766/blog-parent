package com.aguo.blogapi.handle;

import com.aguo.blogapi.pojo.SysUser;
import com.aguo.blogapi.untils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: aguo
 * @DateTime: 2022/5/4 20:14
 * @Description: TODO
 */
//@Component
public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //未登录则直接拦截
        if (loginInterceptor.preHandle(request, response,handler)) return false;
        SysUser sysUser = UserThreadLocal.get();
        //删除文章操作
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
