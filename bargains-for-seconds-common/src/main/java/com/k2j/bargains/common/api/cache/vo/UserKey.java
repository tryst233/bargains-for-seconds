package com.k2j.bargains.common.api.cache.vo;

import java.io.Serializable;

/**
 * @className: UserKey
 * @description: 存储在Redis中用户表的key
 * @author: Sakura
 * @date: 4/6/20
 **/
public class UserKey extends BaseKeyPrefix implements Serializable {

    public UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");

    public static UserKey getByName = new UserKey("name");
}
