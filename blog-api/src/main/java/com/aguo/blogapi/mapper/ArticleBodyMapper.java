package com.aguo.blogapi.mapper;


import com.aguo.blogapi.pojo.ArticleBody;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: aguo
 * @DateTime: 2022/4/30 10:50
 * @Description: TODO
 */
@Mapper
@Repository
public interface ArticleBodyMapper extends BaseMapper<ArticleBody> {
}
