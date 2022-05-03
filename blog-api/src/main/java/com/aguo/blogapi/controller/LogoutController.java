package com.aguo.blogapi.controller;

import com.aguo.blogapi.service.LoginService;
import com.aguo.blogapi.vo.AGuoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: aguo
 * @DateTime: 2022/4/27 9:38
 * @Description: TODO
 */
@RestController
@RequestMapping("logout")
public class LogoutController {
    @Autowired
    private LoginService loginService;

    @RequestMapping
    public AGuoResult logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }
}
