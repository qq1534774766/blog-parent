package com.aguo.blogapi.common.aop;

import com.aguo.blogapi.untils.HttpContextUtil;
import com.aguo.blogapi.untils.IPUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @Author: aguo
 * @DateTime: 2022/5/1 16:28
 * @Description: TODO
 */
@Component
@Aspect //定义通知 （就是需要添加的功能代码 ）和切点（连接点，切入的目标方法）的关系
@Slf4j
public class LogAspect {

//    切入点,也就是标识这个注解为切入点，那么之后但凡被这个注解标识的方法那么就会被作为切入点
    @Pointcut("@annotation(com.aguo.blogapi.common.aop.LogAnnotation)")
    public void pointcut(){}

    //定义通知，这里是环绕通知，即方法的前后都能捕获.
    //对什么方法进行环绕
    @Around("pointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 接着执行原来的方法
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        // 记录日志
        recordLog(joinPoint,endTime-beginTime);
        return result;

    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //LogAnnotation 这只是自定义的注解
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("=====================log start================================");
        log.info("module:{}",logAnnotation.module());
        log.info("operation:{}",logAnnotation.operation());

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method:{}",className + "." + methodName + "()");

//        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        log.info("params:{}",params);

        //获取request 设置IP地址
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        log.info("ip:{}", IPUtil.getIpAddr(request));


        log.info("excute time : {} ms",time);
        log.info("=====================log end================================");
    }


}
