package com.aguo.blogapi.service;

import com.aguo.blogapi.mapper.ArticleMapper;
import com.aguo.blogapi.pojo.Article;
import com.aguo.blogapi.untils.RedisCacheUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @Author: aguo
 * @DateTime: 2022/4/30 17:43
 * @Description: TODO
 */
@Service
@Slf4j
public class ThreadService {
    @Autowired
    private RedisCacheUtil redisUtil;

    @Async("threadPoolTaskExecutor")
    public void increaseArticleViewCount(ArticleMapper articleMapper, Article article) {
        articleMapper.increaseArticleViewCountById(article.getId());
    }

    /**
     *
     * @param cacheId
     * @param simpleName
     * @param methodName
     * @param overallParam
     */
    @Async("threadPoolTaskExecutor")
    public void updateRedis(String cacheId, String simpleName, String methodName, Object overallParam) {
//        articles/listArticle::ArticleController::listArticle::683bcfb2ac2fb2a288f9a40fd5305156

        String redisKey = cacheId + "::" + simpleName + "::" + methodName + "::";
        // 包含了 overallParam != null 的判断
        if (overallParam!=null){
            if (!(overallParam instanceof String)){
                // 不是String类型 则转为Json字符串
                overallParam = JSON.toJSONString(overallParam);
            }
            //overallParam是String 说明overallParam是Json格式的，直接加密即可
            String s = DigestUtils.md5Hex(String.valueOf(overallParam));
            redisUtil.deleteObject(redisKey+ s);
            log.info("删除了缓存,{}", redisKey+ s);
        } else {
            Collection<String> keys = redisUtil.keys(redisKey + "*");
            log.info("删除了所有匹配的缓存,{}", redisKey);
            redisUtil.deleteObject(keys);
        }


    }
}
