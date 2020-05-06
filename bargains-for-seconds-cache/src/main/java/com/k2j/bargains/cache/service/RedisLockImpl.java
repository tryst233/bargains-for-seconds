package com.k2j.bargains.cache.service;

import com.k2j.bargains.common.api.cache.DLockApi;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

/**
 * @className: RedisLockImpl
 * @description: Redis分布式锁
 * @author: Sakura
 * @date: 4/8/20
 **/
@Service(interfaceClass = DLockApi.class)
public class RedisLockImpl implements DLockApi {

    /**
     * @description: 通过连接池对象可以获得对redis的连接
     * @author: Sakura
     * @date: 4/8/20
     * @param null:
     * @return: null
     **/
    @Autowired
    private JedisPool jedisPool;

    private final String LOCK_SUCCESS = "OK";
    private final String SET_IF_NOT_EXIST = "NX";
    private final String SET_WITH_EXPIRE_TIME = "PX";

    private final Long RELEASE_SUCCESS = 1L;

    /**
     * @description: 获取锁
     * @author: Sakura
     * @date: 4/8/20
     * @param lockKey: 锁
     * @param uniqueValue: 能够唯一标识请求的值，以此保证锁的加解锁是同一个客户端
     * @param expireTime: 过期时间, 单位：milliseconds
     * @return: boolean
     **/
    public boolean lock(String lockKey, String uniqueValue, int expireTime) {

        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            // 获取锁
            String result = jedis.set(lockKey, uniqueValue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * @description: 释放锁，使用Lua脚本保证解锁的原子性
     * @author: Sakura
     * @date: 4/8/20
     * @param lockKey: 锁
     * @param uniqueValue: 能够唯一标识请求的值，以此保证锁的加解锁是同一个客户端
     * @return: boolean
     **/
    public boolean unlock(String lockKey, String uniqueValue) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 使用Lua脚本保证操作的原子性
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "return redis.call('del', KEYS[1]) " +
                    "else return 0 " +
                    "end";
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(uniqueValue));

            if (RELEASE_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }
}
