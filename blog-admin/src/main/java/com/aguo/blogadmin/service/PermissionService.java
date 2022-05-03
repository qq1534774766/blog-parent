package com.aguo.blogadmin.service;

import com.aguo.blogadmin.pojo.Permission;
import com.aguo.blogadmin.vo.AGuoResult;
import com.aguo.blogadmin.vo.param.PageParam;

import java.util.List;

/**
 * @Author: aguo
 * @DateTime: 2022/5/3 16:21
 * @Description: TODO
 */
public interface PermissionService {
    /**
     * 列出所有的permission
     * @param pageParam
     * @return
     */
    AGuoResult permissionList(PageParam pageParam);

    AGuoResult add(Permission permission);

    AGuoResult update(Permission permission);

    AGuoResult delete(String id);

    /**
     * 通过用户ID获得权限列表
     * @param adminId
     * @return
     */
    List<Permission> getPermissionByAdminId(Long adminId);
}
