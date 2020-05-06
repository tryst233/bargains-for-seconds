package com.k2j.bargains.common.api.mq.vo;

import com.k2j.bargains.common.api.user.vo.UserVo;

import java.io.Serializable;

/**
 * @className: BgMessage
 * @description: 在MQ中传递的秒杀信息
 * 包含参与秒杀的用户和商品的id
 * @author: Sakura
 * @date: 4/7/20
 **/
public class BgMessage implements Serializable {

    private UserVo user;

    private long goodsId;

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "SkMessage{" +
                "user=" + user +
                ", goodsId=" + goodsId +
                '}';
    }
}
