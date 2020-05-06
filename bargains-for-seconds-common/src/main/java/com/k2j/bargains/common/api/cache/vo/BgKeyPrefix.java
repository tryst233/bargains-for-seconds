package com.k2j.bargains.common.api.cache.vo;

import java.io.Serializable;

/**
 * @className: BgKeyPrefix
 * @description: 判断秒杀状态的key前缀
 * @author: Sakura
 * @date: 4/6/20
 **/
public class BgKeyPrefix extends BaseKeyPrefix implements Serializable {

    public BgKeyPrefix(String prefix) {
        super(prefix);
    }

    public BgKeyPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * @description: 库存为0的商品的前缀
     * @author: Sakura
     * @date: 4/6/20
     * @param null:
     * @return: null
     **/
    public static BgKeyPrefix GOODS_BG_OVER = new BgKeyPrefix("goodsBgOver");

    /**
     * @description: 秒杀接口随机地址
     * @author: Sakura
     * @date: 4/6/20
     * @param null:
     * @return: null
     **/
    public static BgKeyPrefix BG_PATH = new BgKeyPrefix(60, "bgPath");

    /**
     * @description: 验证码5分钟有效
     * @author: Sakura
     * @date: 4/6/20
     * @param null:
     * @return: null
     **/
    public static BgKeyPrefix VERIFY_RESULT = new BgKeyPrefix(300, "verifyResult");
}
