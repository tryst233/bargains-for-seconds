package com.k2j.bargains.common.api.cache;

/**
 * @className: DLockApi
 * @description: 分布式锁接口
 * @author: Sakura
 * @date: 4/5/20
 **/
public interface DLockApi {
    /**
     * @description: 获取锁
     * @author: Sakura
     * @date: 4/5/20
     * @param lockKey: 锁
     * @param uniqueValue: 能够唯一标识请求的值，以此保证锁的上锁和解锁是同一个客户端
     * @param expireTime: 过期时间, 单位：milliseconds
     * @return: boolean
     **/
    boolean lock(String lockKey, String uniqueValue, int expireTime);

    /**
     * @description: 释放锁，使用Lua脚本保证解锁的原子性
     * @author: Sakura
     * @date: 4/5/20
     * @param lockKey: 锁
     * @param uniqueValue: 能够唯一标识请求的值，以此保证锁的上锁和解锁是同一个客户端
     * @return: boolean
     **/
    boolean unlock(String lockKey, String uniqueValue);
}
