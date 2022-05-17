package com.aguo.blogapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: aguo
 * @DateTime: 2022/5/17 20:09
 * @Description: 首页跳转
 */
@Controller
public class IndexController {
    /**
     * 进行首页跳转，这样以后index.html就不用放到该blog-api中的resource中的，彻底动静分离
     * @return
     */
    @RequestMapping({"/"})
    public String toIndex(){
        return "redirect:/static/index.html";
    }
}
