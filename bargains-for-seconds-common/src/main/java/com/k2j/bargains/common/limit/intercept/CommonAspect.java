package com.k2j.bargains.common.limit.intercept;

import com.k2j.bargains.common.limit.RedisLimit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @className: CommonAspect
 * @description: 切面类
 * @author: Sakura
 * @date: 5/2/20
 **/
public class CommonAspect {

    private static Logger logger = LoggerFactory.getLogger(CommonAspect.class);

    @Autowired
    private RedisLimit redisLimit;

    @Pointcut("@annotation(com.k2j.bargains.common.limit.annotation.CommonLimit)")
    private void check() {}

    @Before("check()")
    public void before(JoinPoint joinPoint) throws Exception {

        if (redisLimit == null) {
            throw new NullPointerException("redisLimit is null");
        }

        boolean limit = redisLimit.limit();
        if (!limit) {
            logger.warn("request has bean limited");
            throw new RuntimeException("request has bean limited");
        }
    }
}
