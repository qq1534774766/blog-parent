package com.aguo.blogapi.mapper;

import com.aguo.blogapi.pojo.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: aguo
 * @DateTime: 2022/4/30 10:59
 * @Description: TODO
 */
@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category> {
}
