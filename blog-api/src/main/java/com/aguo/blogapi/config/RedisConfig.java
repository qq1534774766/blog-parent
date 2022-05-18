package com.aguo.blogapi.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: aguo
 * @DateTime: 2022/4/26 22:28
 * @Description: TODO
 */
@Configuration
public class RedisConfig {
    @Autowired
    private RedisProperties redisProperties;
    @Bean
    public RedisTemplate<Object,Object> redisStringTemplate(RedisTemplate<Object, Object> redisTemplate){
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        return redisTemplate;
    }
    @Bean
    public RedisClusterConfiguration redisClusterConfig(){
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.clusterNode("120.76.137.145",6379)
                .clusterNode("120.76.137.145",6380)
                .clusterNode("120.76.137.145",6381);
        redisClusterConfiguration.setMaxRedirects(3);
        redisClusterConfiguration.setPassword("r08874151597");

        return redisClusterConfiguration;
    }


}
