package com.aguo.blogadmin.service.impl;

import com.aguo.blogadmin.mapper.AdminMapper;
import com.aguo.blogadmin.mapper.PermissionMapper;
import com.aguo.blogadmin.pojo.Admin;
import com.aguo.blogadmin.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: aguo
 * @DateTime: 2022/5/3 18:04
 * @Description: TODO
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public Admin getAdminByUsername(String username) {
        LambdaQueryWrapper<Admin> adminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adminLambdaQueryWrapper.eq(Admin::getUsername, username);
        return adminMapper.selectOne(adminLambdaQueryWrapper);
    }


}
