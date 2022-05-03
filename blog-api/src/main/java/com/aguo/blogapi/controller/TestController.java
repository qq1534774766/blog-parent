package com.aguo.blogapi.controller;

import com.aguo.blogapi.untils.UserThreadLocal;
import com.aguo.blogapi.vo.AGuoResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: aguo
 * @DateTime: 2022/4/27 15:10
 * @Description: TODO
 */
@RestController
public class TestController {
    @RequestMapping("/admin")
    public AGuoResult test(){
        System.out.println(UserThreadLocal.get());
        return null;
    }
    @RequestMapping("/admin2")
    public AGuoResult test2(){
        System.out.println(UserThreadLocal.get());
        return null;
    }

}
