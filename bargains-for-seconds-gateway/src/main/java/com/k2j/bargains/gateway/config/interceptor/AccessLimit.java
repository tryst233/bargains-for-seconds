package com.k2j.bargains.gateway.config.interceptor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @description: 用户访问拦截的注解
 * 主要用于防止刷功能的实现
 * @author: Sakura
 * @date: 4/9/20
 **/
@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit {

    /**
     * @description: 两次请求的最大有效时间间隔，即视两次请求为同一状态的时间间隔
     * @author: Sakura
     * @date: 4/9/20
     * @return: int
     **/
    int seconds();

    /**
     * @description: 最大请求次数
     * @author: Sakura
     * @date: 4/9/20
     * @return: int
     **/
    int maxAccessCount();

    /**
     * @description: 是否需要重新登录
     * @author: Sakura
     * @date: 4/9/20
     * @return: boolean
     **/
    boolean needLogin() default true;
}
