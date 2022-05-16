package com.aguo.blogapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Author: aguo
 * @DateTime: 2022/4/30 17:28
 * @Description: TODO
 */
@Configuration
@EnableAsync //开启多线程
public class ThreadPoolConf {
    @Bean("threadPoolTaskExecutor")
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        executor.setCorePoolSize(5);
        //设置最大线程数
        executor.setMaxPoolSize(20);
        //设置活跃线程的时间
        executor.setKeepAliveSeconds(60);
        //配置队列的大小
        executor.setQueueCapacity(Integer.MAX_VALUE);
        //设置线程前缀名称
        executor.setThreadNamePrefix("阿果线程池-");
        //当关闭停止spring容器时，如果线程池仍然有任务，那么不会中断尚未完成的任务
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //初始化线程池
        executor.initialize();
        return executor;
    }
}
