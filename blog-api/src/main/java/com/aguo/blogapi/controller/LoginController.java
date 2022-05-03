package com.aguo.blogapi.controller;

import com.aguo.blogapi.mapper.SysUserMapper;
import com.aguo.blogapi.service.LoginService;
import com.aguo.blogapi.vo.AGuoResult;
import com.aguo.blogapi.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: aguo
 * @DateTime: 2022/4/26 13:21
 * @Description: TODO
 */
@RestController
@RequestMapping("login")
public class LoginController {
//    高内聚，低耦合，userservice就只是负责user表相关操作,不负责登录
//    @Autowired
//    private SysUserMapper sysUserMapper;

    @Autowired
    private LoginService loginService;
    /**
     * 登录功能
     * @return
     */
    @PostMapping("")
    public AGuoResult login(@RequestBody @Validated LoginParam loginParam){
        return loginService.login(loginParam);
    }
}
