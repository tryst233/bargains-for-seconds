package com.k2j.bargains.cache.service;

import com.k2j.bargains.common.api.cache.RedisServiceApi;
import com.k2j.bargains.common.api.cache.vo.KeyPrefix;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static com.k2j.bargains.common.util.JsonUtil.beanToString;
import static com.k2j.bargains.common.util.JsonUtil.stringToBean;

/**
 * @className: RedisServiceImpl
 * @description: redis服务实现
 * @author: Sakura
 * @date: 4/8/20
 **/
@Service(interfaceClass = RedisServiceApi.class)
public class RedisServiceImpl implements RedisServiceApi {

    /**
     * @description: 通过连接池对象可以获得对redis的连接
     * @author: Sakura
     * @date: 4/8/20
     * @param null:
     * @return: null
     **/
    @Autowired
    JedisPool jedisPool;

    /**
     * @description: get操作
     * @author: Sakura
     * @date: 4/8/20
     * @param prefix: 前缀
     * @param key: 键
     * @param clazz:
     * @return: T
     **/
    @Override
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;// redis连接池

        try {
            jedis = jedisPool.getResource();
            // 生成真正的存储于redis中的key
            String realKey = prefix.getPrefix() + key;
            // 通过key获取存储于redis中的对象（这个对象是以json格式存储的，所以是字符串）
            String strValue = jedis.get(realKey);
            // 将json字符串转换为对应的对象
            T objValue = stringToBean(strValue, clazz);
            return objValue;
        } finally {
            // 归还redis连接到连接池
            returnToPool(jedis);
        }
    }

    /**
     * @description: 将redis连接对象归还到redis连接池
     * @author: Sakura
     * @date: 4/8/20
     * @param jedis:
     * @return: void
     **/
    private void returnToPool(Jedis jedis) {
        if (jedis != null)
            jedis.close();
    }

    @Override
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 将对象转换为json字符串
            String strValue = beanToString(value);

            if (strValue == null || strValue.length() <= 0)
                return false;

            // 生成实际存储于redis中的key
            String realKey = prefix.getPrefix() + key;
            // 获取key的过期时间
            int expires = prefix.expireSeconds();

            if (expires <= 0) {
                // 设置key的过期时间为redis默认值（由redis的缓存策略控制）
                jedis.set(realKey, strValue);
            } else {
                // 设置key的过期时间
                jedis.setex(realKey, expires, strValue);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    @Override
    public boolean exists(KeyPrefix keyPrefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = keyPrefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    @Override
    public long incr(KeyPrefix keyPrefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = keyPrefix.getPrefix() + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    @Override
    public long decr(KeyPrefix keyPrefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = keyPrefix.getPrefix() + key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    @Override
    public boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            Long del = jedis.del(realKey);
            return del > 0;
        } finally {
            returnToPool(jedis);
        }
    }

}
