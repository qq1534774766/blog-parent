package com.aguo.blogadmin.controller;

import com.aguo.blogadmin.pojo.Permission;
import com.aguo.blogadmin.service.PermissionService;
import com.aguo.blogadmin.vo.AGuoResult;
import com.aguo.blogadmin.vo.param.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: aguo
 * @DateTime: 2022/5/3 16:18
 * @Description: 后台控制器
 */
@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping("permission/permissionList")
    public AGuoResult permissionList(@RequestBody PageParam pageParam){
        return permissionService.permissionList(pageParam);
    }
    @PostMapping("permission/add")
    public AGuoResult add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }
    @PostMapping("permission/update")
    public AGuoResult update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }
    @GetMapping("permission/delete/{id}")
    public AGuoResult delete(@PathVariable("id") String id){
        return permissionService.delete(id);
    }
}
