package com.k2j.bargains.common.api.order.vo;

import com.k2j.bargains.common.api.goods.vo.GoodsVo;
import com.k2j.bargains.common.api.user.vo.UserVo;
import com.k2j.bargains.common.domain.OrderInfo;

/**
 * @className: OrderDetailVo
 * @description: 订单详情，包含订单信息和商品信息
 * 用于将数据传递给客户端
 * @author: Sakura
 * @date: 4/7/20
 **/
public class OrderDetailVo {

    /**
     * @description: 用户信息
     * @author: Sakura
     * @date: 4/7/20
     * @param null:
     * @return: null
     **/
    private UserVo user;

    /**
     * @description: 商品信息
     * @author: Sakura
     * @date: 4/7/20
     * @param null:
     * @return: null
     **/
    private GoodsVo goods;

    /**
     * @description: 订单信息
     * @author: Sakura
     * @date: 4/7/20
     * @param null:
     * @return: null
     **/
    private OrderInfo order;

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public OrderInfo getOrder() {
        return order;
    }

    public void setOrder(OrderInfo order) {
        this.order = order;
    }
}
