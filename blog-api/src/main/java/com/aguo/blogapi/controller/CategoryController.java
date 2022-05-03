package com.aguo.blogapi.controller;

import com.aguo.blogapi.service.CategoryService;
import com.aguo.blogapi.vo.AGuoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: aguo
 * @DateTime: 2022/5/1 12:05
 * @Description: TODO
 */
@RestController
@RequestMapping("categorys")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping
    public AGuoResult categoryList(){
        return categoryService.listCategory();
    }

    @RequestMapping("detail")
    public AGuoResult categoryDetail(){
        return categoryService.listCategoryDetail();
    }
    @RequestMapping("detail/{id}")
    public AGuoResult getCategoryDetailById(@PathVariable("id") Long categoryId){
        return categoryService.getCategoryDetailById(categoryId);
    }
}
