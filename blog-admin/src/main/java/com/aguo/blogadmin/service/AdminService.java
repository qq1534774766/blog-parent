package com.aguo.blogadmin.service;

import com.aguo.blogadmin.pojo.Admin;

/**
 * @Author: aguo
 * @DateTime: 2022/5/3 18:01
 * @Description: TODO
 */
public interface AdminService {

    Admin getAdminByUsername(String username);
}
