package com.aguo.blogapi.common.aop;

import java.lang.annotation.*;

/**
 * @Author: aguo
 * @DateTime: 2022/5/1 16:24
 * @Description: TODO
 */
//Target ElementType.METHOD：表示注解到方法
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String module() default "";

    String operation() default "";

}
