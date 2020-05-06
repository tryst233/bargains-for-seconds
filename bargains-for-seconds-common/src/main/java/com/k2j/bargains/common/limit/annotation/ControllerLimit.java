package com.k2j.bargains.common.limit.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerLimit {

    /**
     * @description: Error code
     * @author: Sakura
     * @date: 5/2/20
     * @return: int
     **/
    int errorCode() default 500;
}
