package com.aguo.blogapi.service.impl;

import com.aguo.blogapi.mapper.CategoryMapper;
import com.aguo.blogapi.pojo.Category;
import com.aguo.blogapi.service.CategoryService;
import com.aguo.blogapi.vo.AGuoResult;
import com.aguo.blogapi.vo.CategoryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: aguo
 * @DateTime: 2022/4/30 10:58
 * @Description: TODO
 */
@Service
public class CategoryServiceIml implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo getCategoryVo(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        return copy(category);
    }

    @Override
    public AGuoResult listCategory(){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Category::getId,Category::getCategoryName);
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        return AGuoResult.success(copyList(categories));
    }

    @Override
    public AGuoResult listCategoryDetail() {
        List<Category> categories = categoryMapper.selectList(null);
        return AGuoResult.success(copyList(categories));
    }

    @Override
    public AGuoResult getCategoryDetailById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        return AGuoResult.success(copy(category));
    }

    private List<CategoryVo> copyList(List<Category> categories) {
        ArrayList<CategoryVo> categoryVos = new ArrayList<>();
        for (Category categoryVar : categories) {
            categoryVos.add(copy(categoryVar));
        }
        return categoryVos;
    }

    private CategoryVo copy(Category categoryVar) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(categoryVar, categoryVo);
        categoryVo.setId(String.valueOf(categoryVar.getId()));
        return categoryVo;
    }

}
