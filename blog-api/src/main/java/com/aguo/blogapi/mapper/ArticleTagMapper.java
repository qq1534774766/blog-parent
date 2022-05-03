package com.aguo.blogapi.mapper;

import com.aguo.blogapi.pojo.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: aguo
 * @DateTime: 2022/5/1 14:47
 * @Description: TODO
 */
@Mapper
@Repository
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}
