package com.aguo.blogapi.common.aop;

import com.aguo.blogapi.service.ThreadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: aguo
 * @DateTime: 2022/5/4 0:55
 * @Description: TODO
 */
@Aspect
@Component
@Slf4j
public class DoubleDeleteDelayAspect {
    @Autowired
    private ThreadService threadService;
    @Pointcut("@annotation(com.aguo.blogapi.common.aop.DoubleDeleteDelay)")
    public void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Signature signature = joinPoint.getSignature();
        String methodName =  signature.getName();
        //存放方法所有的类
        Class[] parameterTypes = new Class[joinPoint.getArgs().length];
        Object[] args = joinPoint.getArgs();
        //获取参数Class类数组
        for(int i=0; i<args.length; i++) {
            if(args[i] != null) {
                parameterTypes[i] = args[i].getClass();
            }else {
                parameterTypes[i] = null;
            }
        }
        //得到延迟双删的注解，以便获得参数值
        DoubleDeleteDelay annotation = signature.getDeclaringType().getMethod(methodName,parameterTypes).getAnnotation(DoubleDeleteDelay.class);
        String cacheId = annotation.cacheId();
        String simpleClassName = annotation.simpleClassName();
        String redisMethodName = annotation.methodName();
        String jsonParam = annotation.jsonParam();
        Object result;
        log.info("开始延迟双删");
        if (StringUtils.isNotEmpty(jsonParam)){
//            jsonParam = DigestUtils.md5Hex(jsonParam);专业的事交给专业的人做，我们要给负责执行删除缓存方法来执行这个方法，这里不能执行
            log.info("第一次删除缓存 ↓");
            // 这里使用的是线程池异步方式，但是似乎这样不对，因为延迟双删要的是即时性。纯粹练手
            threadService.deleteRedis(cacheId, simpleClassName,redisMethodName,jsonParam);
            //原方法执行
            result = joinPoint.proceed();
            log.info("第二次删除缓存 ↓");
            threadService.deleteRedis(cacheId, simpleClassName,redisMethodName,jsonParam);
            return result;
        }
        log.info("第一次删除缓存 ↓");
        threadService.deleteRedis(cacheId, simpleClassName,redisMethodName,null);
        result = joinPoint.proceed();
        log.info("第二次删除缓存 ↓");
        threadService.deleteRedis(cacheId, simpleClassName,redisMethodName,null);
        return result;
    }
}
