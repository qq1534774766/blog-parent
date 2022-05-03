package com.aguo.blogapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: aguo
 * @DateTime: 2022/4/26 22:28
 * @Description: TODO
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<Object,Object> redisStringTemplate(RedisTemplate<Object, Object> redisTemplate){
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        return redisTemplate;
    }
}
