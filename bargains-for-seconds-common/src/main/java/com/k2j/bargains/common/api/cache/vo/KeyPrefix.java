package com.k2j.bargains.common.api.cache.vo;

/**
 * @className: KeyPrefix
 * @description: redis 键的前缀，避免出现相同 key 的情形，可通过前缀加以区分
 * @author: Sakura
 * @date: 4/5/20
 **/
public interface KeyPrefix {
    /**
     * @description: key的过期时间
     * @author: Sakura
     * @date: 4/5/20

     * @return: int
     **/
    int expireSeconds();

    /**
     * @description: key的前缀
     * @author: Sakura
     * @date: 4/5/20

     * @return: java.lang.String
     **/
    String getPrefix();
}
