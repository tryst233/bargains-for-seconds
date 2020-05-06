package com.k2j.bargains.common.api.cache.vo;

import java.io.Serializable;

/**
 * @className: GoodsKeyPrefix
 * @description: 存储在redis中的商品信息的key
 * @author: Sakura
 * @date: 4/6/20
 **/
public class GoodsKeyPrefix extends BaseKeyPrefix implements Serializable {

    public GoodsKeyPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * @description: 缓存在redis中的商品列表页面的key的前缀
     * @author: Sakura
     * @date: 4/6/20
     * @param null:
     * @return: null
     **/
    public static GoodsKeyPrefix GOODS_LIST_HTML = new GoodsKeyPrefix(60, "goodsListHtml");

    /**
     * @description: 缓存在redis中的商品详情页面的key的前缀
     * @author: Sakura
     * @date: 4/6/20
     * @param null:
     * @return: null
     **/
    public static GoodsKeyPrefix goodsDetailKeyPrefix = new GoodsKeyPrefix(60, "goodsDetail");

    /**
     * @description: 缓存在redis中的商品库存的前缀(缓存过期时间为永久)
     * @author: Sakura
     * @date: 4/6/20
     * @param null:
     * @return: null
     **/
    public static GoodsKeyPrefix GOODS_STOCK = new GoodsKeyPrefix(0, "goodsStock");

}
