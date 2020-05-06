package com.k2j.bargains.common.api.cache;

import com.k2j.bargains.common.api.cache.vo.KeyPrefix;

/**
 * @className: RedisServiceApi
 * @description: Redis服务接口
 * @author: Sakura
 * @date: 4/5/20
 **/
public interface RedisServiceApi {

    /**
     * @description: redis的get操作，通过key获取存储在redis中的对象
     * @author: Sakura
     * @date: 4/5/20
     * @param prefix: key的前缀
     * @param key: 业务层传入的key
     * @param clazz: 存储在redis中的对象类型（redis中是以字符串存储的）
     * @return: T 存储于redis中的对象
     **/
    <T> T get(KeyPrefix prefix, String key, Class<T> clazz);

    /**
     * @description: redis的set操作
     * @author: Sakura
     * @date: 4/5/20
     * @param prefix: key的前缀
     * @param key: 键
     * @param value: 值
     * @return: boolean 操作成功为true，否则为false
     **/
    <T> boolean set(KeyPrefix prefix, String key, T value);

    /**
     * @description: 判断key是否存在于redis中
     * @author: Sakura
     * @date: 4/5/20
     * @param keyPrefix: key的前缀
     * @param key: 键
     * @return: boolean
     **/
    boolean exists(KeyPrefix keyPrefix, String key);

    /**
     * @description: 自增
     * @author: Sakura
     * @date: 4/5/20
     * @param keyPrefix: key的前缀
     * @param key: 键
     * @return: long
     **/
    long incr(KeyPrefix keyPrefix, String key);

    /**
     * @description: 自减
     * @author: Sakura
     * @date: 4/5/20
     * @param keyPrefix: key的前缀
     * @param key: 键
     * @return: long
     **/
    long decr(KeyPrefix keyPrefix, String key);

    /**
     * @description: 删除缓存中的用户数据
     * @author: Sakura
     * @date: 4/5/20
     * @param prefix: key的前缀
     * @param key: 键
     * @return: boolean
     **/
    boolean delete(KeyPrefix prefix, String key);

}
