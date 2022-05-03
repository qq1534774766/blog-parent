package com.aguo.blogadmin.service;

import com.aguo.blogadmin.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @Author: aguo
 * @DateTime: 2022/5/3 17:57
 * @Description: TODO
 */
@Service
public class SecurityUserService implements UserDetailsService {
    @Autowired
    private AdminService adminService;
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
//        通过业务层查询用户
        Admin admin = adminService.getAdminByUsername(username);
        if (admin == null) {
//        如果不存在用户，则返回null,登录失败
            return null;
        }
//        返回Admin对象，并且set密码，交给spring Security来验证账号密码,authorities这是授权验证功能
        User user = new User(username,admin.getPassword(),new ArrayList<>());
        return user;
    }
}
