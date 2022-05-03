package com.aguo.blogapi.service;

import com.aguo.blogapi.mapper.ArticleMapper;
import com.aguo.blogapi.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: aguo
 * @DateTime: 2022/4/30 17:43
 * @Description: TODO
 */
@Service
public class ThreadService {
    @Async("taskExecutor")
    public void increaseArticleViewCount(ArticleMapper articleMapper, Article article){
        articleMapper.increaseArticleViewCountById(article.getId());
    }
}
