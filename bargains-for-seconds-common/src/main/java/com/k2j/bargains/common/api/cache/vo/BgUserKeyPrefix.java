package com.k2j.bargains.common.api.cache.vo;

import java.io.Serializable;

/**
 * @className: BgUserKeyPrefix
 * @description: 秒杀用户信息的key前缀
 * @author: Sakura
 * @date: 4/6/20
 **/
public class BgUserKeyPrefix extends BaseKeyPrefix implements Serializable {

    public static final int TOKEN_EXPIRE = 30 * 60; //缓存有效时间为30min

    public BgUserKeyPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * @description: 用户cookie
     * @author: Sakura
     * @date: 4/6/20
     * @param null:
     * @return: null
     **/
    public static BgUserKeyPrefix TOKEN = new BgUserKeyPrefix(TOKEN_EXPIRE, "token");

    /**
     * @description: 用于将用户对象存储到redis中的key前缀
     * @author: Sakura
     * @date: 4/6/20
     * @param null:
     * @return: null
     **/
    public static BgUserKeyPrefix BG_USER_PHONE = new BgUserKeyPrefix(0, "id");
}
