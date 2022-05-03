package com.aguo.blogapi.mapper;

import com.aguo.blogapi.dos.Archives;
import com.aguo.blogapi.pojo.Article;
import com.aguo.blogapi.vo.params.PageParams;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: aguo
 * @DateTime: 2022/4/24 20:27
 * @Description: TODO
 */
@Mapper
@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 文章归档
     * @return
     */
    List<Archives> listArchives();

    /**
     * 通过文章的id增加文章的阅读量，每次增加1，线程安全
     * @param id
     */
    void increaseArticleViewCountById(Long id);


    /**
     *
     *
     * @param page
     * @param pageParams
     * @return
     */
    Page<Article> listArticle(@Param("page") Page<Article> page,
                              @Param("pageParams") PageParams pageParams);
}
