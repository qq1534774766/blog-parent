package com.aguo.blogapi.controller;

import com.aguo.blogapi.service.LoginService;
import com.aguo.blogapi.vo.AGuoResult;
import com.aguo.blogapi.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: aguo
 * @DateTime: 2022/4/27 10:02
 * @Description: TODO
 */
@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    private LoginService loginService;

    @RequestMapping
    public AGuoResult register(@RequestBody @Validated LoginParam param){
        return loginService.register(param);
    }
}
