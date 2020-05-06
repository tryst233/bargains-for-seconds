package com.k2j.bargains.common.api.cache.vo;

import java.io.Serializable;

/**
 * @className: AccessKeyPrefix
 * @description: 访问次数的key前缀
 * @author: Sakura
 * @date: 4/6/20
 **/
public class AccessKeyPrefix extends BaseKeyPrefix implements Serializable {

    public AccessKeyPrefix(String prefix) {
        super(prefix);
    }

    public AccessKeyPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * @description: 可灵活设置过期时间
     * @author: Sakura
     * @date: 4/6/20
     * @param expireSeconds:
     * @return: com.k2j.bargains.common.api.cache.vo.AccessKeyPrefix
     **/
    public static AccessKeyPrefix withExpire(int expireSeconds) {
        return new AccessKeyPrefix(expireSeconds, "access");
    }

}
