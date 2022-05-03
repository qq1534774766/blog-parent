package com.aguo.blogapi.service;

import com.aguo.blogapi.vo.AGuoResult;
import com.aguo.blogapi.vo.CategoryVo;

/**
 * @Author: aguo
 * @DateTime: 2022/4/30 10:58
 * @Description: TODO
 */
public interface CategoryService {
    /**
     * 通过分类id获取分类信息
     * @param categoryId
     * @return
     */
    CategoryVo getCategoryVo(Long categoryId);

    /**
     * 查询所有分类，但是只包含id与分类名
     * @return
     */
    AGuoResult listCategory();

    /**
     * 查询所有分类，包含分类实体的所有信息
     * @return
     */
    AGuoResult listCategoryDetail();

    /**
     * 通过分类Id获取分类的详细信息
     * @return
     * @param categoryId
     */
    AGuoResult getCategoryDetailById(Long categoryId);
}
