package com.aguo.blogapi.common.timedTask;

import com.aguo.blogapi.service.ArticleService;
import com.aguo.blogapi.untils.RedisCacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Author: aguo
 * @DateTime: 2022/5/4 11:37
 * @Description: TODO
 */
@Component
@EnableScheduling   // 1.开启定时任务
//@EnableAsync        // 2.开启多线程
@Slf4j
public class RedisTimedTask {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisCacheUtil redisUtil;

    @Async("threadPoolTaskExecutor")
    @Scheduled(cron = "0/5 * * * * ?")
    public void updateArticleViewCountFromCacheToDB(){
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String timedTaskKey = "blog:article:"+currentMethodName;

        if (redisUtil.existsKey(timedTaskKey)) {
            String redisKey = "blog:article:viewCount:articleId:*";
            Collection<String> keys = redisUtil.keys(redisKey);
            if (keys != null && keys.size() > 0) {
                for (String key : keys) {
                    String viewCount = redisUtil.getCacheObject(key);
                    String articleId = StringUtils.split(key, ":")[4];
                    articleService.updateArticleViewCount(Long.parseLong(articleId), Integer.valueOf(viewCount));
                    log.info("执行定时任务【{}】==>阅读量【{}】写入数据库,文章ID：【{}】",currentMethodName, viewCount, articleId);
                }
            }
            else {
                redisUtil.deleteObject(timedTaskKey);
                log.info("暂停定时任务【{}】",currentMethodName);
            }
        }
    }
}
