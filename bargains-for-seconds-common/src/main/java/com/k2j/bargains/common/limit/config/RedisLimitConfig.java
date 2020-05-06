package com.k2j.bargains.common.limit.config;

import com.k2j.bargains.common.limit.RedisLimit;
import com.k2j.bargains.common.limit.constant.RedisToolsConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import javax.annotation.Resource;

/**
 * @className: RedisLimitConfig
 * @description:
 * @author: Sakura
 * @date: 5/2/20
 **/
@Configuration
public class RedisLimitConfig {

    private Logger logger = LoggerFactory.getLogger(RedisLimitConfig.class);

    @Value("${redis.limit}")
    private int limit;

    @Resource
    private JedisConnectionFactory jedisConnectionFactory;

    @Bean
    public RedisLimit build() {
        RedisLimit redisLimit = new RedisLimit.Builder(jedisConnectionFactory, RedisToolsConstant.SINGLE)
                .limit(limit)
                .build();
        return redisLimit;
    }
}
