package com.aguo.blogapi.service;

import com.aguo.blogapi.mapper.ArticleMapper;
import com.aguo.blogapi.pojo.Article;
import com.aguo.blogapi.untils.RedisCacheUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

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

    /**
     * 使用redis暂存文章的阅读量
     * 定时器会定时的将redis的阅读数持久到数据库中
     * @param articleMapper
     * @param article
     */
    @Async("threadPoolTaskExecutor")
    public void increaseArticleViewCount(ArticleMapper articleMapper, Article article) {
        //启动定时器
        enableTimedTasks("article","updateArticleViewCountFromCacheToDB");
        String redisKey ="blog:article:viewCount:articleId:"+ article.getId();
        String value =  redisUtil.getCacheObject(redisKey);
        if (StringUtils.isNotEmpty(value)){
            //阅读量++
            redisUtil.increaseValue(redisKey);
        }else {
            //设置阅读量,60分钟
            redisUtil.setCacheObject(redisKey,String.valueOf(article.getViewCounts()+1),11L, TimeUnit.SECONDS);
        }
    }


    /**
     * 开启指定的定时器器
     * 原理：
     * <p>
     *  定时器本质上一直在定时执行任务，只不过设置一个timedTaskKey，
     * 通过判断timedTaskKey是否存在来决定定时器是否执行[其余代码]，从而优化定时器的执行速度
     *<p/>
     * @param type 定时器的类型，比如负责文章浏览量的，那就是article
     * @param timedTaskName 定时器的名称
     */
    @Async("threadPoolTaskExecutor")
    public void enableTimedTasks(String type,String timedTaskName){
        String timedTaskKey ="blog:"+type+":"+ timedTaskName;
        Boolean success =  redisUtil.setCacheObjectIfAbsent(timedTaskKey, "1");
        if (success) {
            log.info("启动定时任务【{}】", timedTaskName);
        }
    }

    /**
     * 删除与参数匹配的缓存Key
     * @param cacheId
     * @param simpleName
     * @param methodName
     * @param overallParam 未经加密
     */
    @Async("threadPoolTaskExecutor")
    public void deleteRedis(String cacheId, String simpleName, String methodName, Object overallParam) {
//        articles/listArticle:ArticleController:listArticle:683bcfb2ac2fb2a288f9a40fd5305156
        String redisKey = cacheId + ":" + simpleName + ":" + methodName + ":";
        // 包含了 overallParam != null 的判断
        if (overallParam != null) {
            if (!(overallParam instanceof String)) {
                // 不是String类型 则转为Json字符串
                overallParam = JSON.toJSONString(overallParam);
            }
            //overallParam是String 说明overallParam是Json格式的，直接加密即可
            String s = DigestUtils.md5Hex(String.valueOf(overallParam));
            redisUtil.deleteObject(redisKey + s);
            log.info("删除了缓存,{}", redisKey + s);
        } else {
            Collection<String> keys = redisUtil.keys(redisKey + "*");
            redisUtil.deleteObject(keys);
            log.info("删除了所有匹配的缓存,{}", redisKey);
        }
    }






}
