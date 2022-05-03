package com.aguo.blogapi.common.cache;

import com.aguo.blogapi.untils.RedisCacheUtil;
import com.aguo.blogapi.vo.AGuoResult;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @Author: aguo
 * @DateTime: 2022/5/2 16:14
 * @Description: TODO
 */
@Component
@Aspect //定义通知 （就是需要添加的功能代码 ）和切点（连接点，切入的目标方法）的关系
@Slf4j
public class CacheAspect {
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisCacheUtil redisCacheUtil;
    
    @Pointcut("@annotation(com.aguo.blogapi.common.cache.Cache)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        try {
            Signature signature = joinPoint.getSignature();
            //类名
            String className = joinPoint.getTarget().getClass().getSimpleName();
            //调用的方法名
            String methodName = signature.getName();
            Class[] parameterTypes = new Class[joinPoint.getArgs().length];

            Object[] args = joinPoint.getArgs();
            //参数
            String params = "";
            for(int i=0; i<args.length; i++) {
                if(args[i] != null) {
                    params += JSON.toJSONString(args[i]);
                    parameterTypes[i] = args[i].getClass();
                }else {
                    parameterTypes[i] = null;
                }
            }
            if (StringUtils.isNotEmpty(params)) {
                //加密 以防出现key过长以及字符转义获取不到的情况
                params = DigestUtils.md5Hex(params);
            }
            Method method = joinPoint.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
            //获取Cache注解
            Cache annotation = method.getAnnotation(Cache.class);
            //缓存过期时间
            long expire = annotation.expire();
            //缓存名称
            String name = annotation.cacheId();
            //先从redis获取
            String redisKey = name + "::" + className+"::"+methodName+"::"+params;

//            String redisValue = redisTemplate.opsForValue().get(redisKey);
            String redisValue = redisCacheUtil.getCacheObject(redisKey);

            if (StringUtils.isNotEmpty(redisValue)){
                log.info("走了缓存~~~,{},{}",className,methodName);
                log.info("缓存key为,{}",redisKey);
                return JSON.parseObject(redisValue, AGuoResult.class);
            }

            Object proceed = joinPoint.proceed();
//            redisTemplate.opsForValue().set(redisKey,JSON.toJSONString(proceed), Duration.ofMillis(expire));
            redisCacheUtil.setCacheObject(redisKey,JSON.toJSONString(proceed), expire, TimeUnit.SECONDS);
            log.info("存入缓存~~~ {},{}",className,methodName);
            log.info("缓存key为,{}",redisKey);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return AGuoResult.failed(-999,"系统错误");
    }
}
