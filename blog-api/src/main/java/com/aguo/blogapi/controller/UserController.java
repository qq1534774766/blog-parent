package com.aguo.blogapi.controller;

import com.aguo.blogapi.service.SysUserService;
import com.aguo.blogapi.vo.AGuoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: aguo
 * @DateTime: 2022/4/26 21:44
 * @Description: TODO
 */
@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取当前用户的信息
     * @RequestHeader("Authorization") 获取http请求头的Authorization的值
     * @param token
     * @return id，account，nickname，avatar
     */
    @GetMapping("/currentUser")
    public AGuoResult currentUser(@RequestHeader("Authorization") String token){
        return sysUserService.findUserByToken(token);
    }
}
