package com.aguo.blogapi.common.cache;

import java.lang.annotation.*;

/**
 * @Author: aguo
 * @DateTime: 2022/5/2 16:11
 * @Description: TODO
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface  Cache {

    /**
     * 单位是秒,默认60秒
     * @return
     */
    long expire() default 60 ;

    String cacheId();

}