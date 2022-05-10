package com.aguo.blogapi.common.aop;

import java.lang.annotation.*;

/**
 * @Author: aguo
 * @DateTime: 2022/5/4 0:52
 * @Description: 延迟双删
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DoubleDeleteDelay {
        String cacheId();
        String simpleClassName();
        String methodName();

        /**
         * 必须是Json格式的字符串类
         * @return
         */
        String jsonParam() default "";
}
