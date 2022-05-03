package com.aguo.blogapi.service;

import com.aguo.blogapi.vo.AGuoResult;
import com.aguo.blogapi.vo.params.ArticleParam;
import com.aguo.blogapi.vo.params.PageParams;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: aguo
 * @DateTime: 2022/4/24 20:49
 * @Description: TODO
 */
public interface  ArticleService {
    /**
     * 分页查询 文章列表
     * @param pageParams 默认1,10
     * @return
     */
    AGuoResult listArticle(PageParams pageParams);

    /**
     * 最热门文章
     * @param limit
     * @return
     */
    AGuoResult hotArticles(int limit);

    /**
     * 最新文章标签
     * @param limit
     * @return
     */
    AGuoResult newArticles(int limit);

    /**
     * 文章归档卡片
     * @return
     */
    AGuoResult listArchives();

    /**
     * 查询文章的详细内容
     * @param articleId
     * @return
     */
    AGuoResult findArticleById(Long articleId);

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
//     articles/listArticle","ArticleController","listArticle",null);
    @Transactional
    AGuoResult publish(ArticleParam articleParam);
}
