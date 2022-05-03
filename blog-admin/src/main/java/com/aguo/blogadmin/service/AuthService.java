package com.aguo.blogadmin.service;

import com.aguo.blogadmin.pojo.Admin;
import com.aguo.blogadmin.pojo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: aguo
 * @DateTime: 2022/5/3 18:11
 * @Description: TODO
 */
@Service
public class AuthService {
    @Autowired
    private AdminService adminService;

    @Autowired
    private PermissionService permissionService;

    public boolean auth(HttpServletRequest request, Authentication auth){
    //1.权限验证
        // 请求路径
        String requestURI = request.getRequestURI();
        Object principal = auth.getPrincipal();
        if(principal == null || "anonymousUser".equals(principal.toString())){
            //没登录
            return false;
        }

        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        Admin admin = adminService.getAdminByUsername(username);
        if(admin == null){
            return false;
        }
        Long adminId = admin.getId();
        // 【root用户】，直接放行
        if (adminId==1) {
            return true;
        }
        // 【普通用户】，需要验证
        // 获取用户的权限列表
        List<Permission> permissions = permissionService.getPermissionByAdminId(adminId);
        // /admin/list?a=1&b=2 转为   /admin/list
        requestURI = StringUtils.substringBefore(requestURI, "?");
        //验证
        for (Permission permission : permissions) {
            if (requestURI.equals(permission.getPath())){
                return true;
            }
        }

        return false;
    }
}
