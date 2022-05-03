package com.aguo.blogadmin.service.impl;

import com.aguo.blogadmin.mapper.PermissionMapper;
import com.aguo.blogadmin.pojo.Permission;
import com.aguo.blogadmin.service.PermissionService;
import com.aguo.blogadmin.vo.AGuoResult;
import com.aguo.blogadmin.vo.PageResult;
import com.aguo.blogadmin.vo.param.PageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: aguo
 * @DateTime: 2022/5/3 16:22
 * @Description: TODO
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public AGuoResult permissionList(PageParam pageParam) {
        Page<Permission> page = new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper =null;
        if (StringUtils.isNotEmpty(pageParam.getQueryString())) {
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Permission::getName,pageParam.getQueryString());
        }
        Page<Permission> page1 = permissionMapper.selectPage(page, queryWrapper);
        PageResult<Permission> permissionPageResult = new PageResult<Permission>();
        permissionPageResult.setList(page1.getRecords());
        permissionPageResult.setTotal(page1.getTotal());

        return AGuoResult.success(permissionPageResult );
    }

    @Override
    public AGuoResult add(Permission permission) {
        return permissionMapper.insert(permission)>0 ? AGuoResult.success(null):AGuoResult.fail(405,"添加失败");
    }

    @Override
    public AGuoResult update(Permission permission) {
        return permissionMapper.updateById(permission)>0 ? AGuoResult.success(null):AGuoResult.fail(406,"更新失败");

    }

    @Override
    public AGuoResult delete(String id) {
        return permissionMapper.deleteById(id) >0 ? AGuoResult.success(null):AGuoResult.fail(407,"删除失败");
    }
}
