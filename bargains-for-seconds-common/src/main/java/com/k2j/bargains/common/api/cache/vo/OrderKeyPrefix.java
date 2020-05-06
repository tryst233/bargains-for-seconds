package com.k2j.bargains.common.api.cache.vo;

import java.io.Serializable;

/**
 * @className: OrderKeyPrefix
 * @description: 存储在Redis中订单的key前缀
 * @author: Sakura
 * @date: 4/6/20
 **/
public class OrderKeyPrefix extends BaseKeyPrefix implements Serializable {

    public OrderKeyPrefix(String prefix) {
        super(prefix);
    }

    public OrderKeyPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * @description: 秒杀订单信息的前缀
     * @author: Sakura
     * @date: 4/6/20
     * @param null:
     * @return: null
     **/
    public static OrderKeyPrefix BARGAINS_ORDER = new OrderKeyPrefix("bgOrder");
}
