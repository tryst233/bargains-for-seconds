package com.k2j.bargains.common.api.goods.vo;

import com.k2j.bargains.common.api.user.vo.UserVo;

import java.io.Serializable;

/**
 * @className: GoodsDetailVo
 * @description: 商品详情，用于将数据传递给客户端
 * @author: Sakura
 * @date: 4/6/20
 **/
public class GoodsDetailVo implements Serializable {

    private int bargainsStatus = 0;
    private int remainSeconds = 0;
    private GoodsVo goods;
    private UserVo user;

    public int getBargainsStatus() {
        return bargainsStatus;
    }

    public void setBargainsStatus(int bargainsStatus) {
        this.bargainsStatus = bargainsStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "GoodsDetailVo{" +
                "bargainsStatus=" + bargainsStatus +
                ", remainSeconds=" + remainSeconds +
                ", goods=" + goods +
                ", user=" + user +
                '}';
    }
}
