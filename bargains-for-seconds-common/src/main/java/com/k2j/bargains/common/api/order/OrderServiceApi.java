package com.k2j.bargains.common.api.order;

import com.k2j.bargains.common.api.goods.vo.GoodsVo;
import com.k2j.bargains.common.api.user.vo.UserVo;
import com.k2j.bargains.common.domain.BargainsOrder;
import com.k2j.bargains.common.domain.OrderInfo;

/**
 * @className: OrderServiceApi
 * @description: 订单服务接口
 * @author: Sakura
 * @date: 4/7/20
 **/
public interface OrderServiceApi {

    /**
     * @description: 通过订单id获取订单
     * @author: Sakura
     * @date: 4/7/20
     * @param orderId:
     * @return: com.k2j.bargains.common.domain.OrderInfo
     **/
    OrderInfo getOrderById(long orderId);

    /**
     * @description: 通过用户id与商品id从订单列表中获取订单信息，这个地方用到了唯一索引（unique index）
     * @author: Sakura
     * @date: 4/7/20
     * @param userId:
     * @param goodsId:
     * @return: com.k2j.bargains.common.domain.BargainsOrder
     **/
    BargainsOrder getBargainsOrderByUserIdAndGoodsId(long userId, long goodsId);

    /**
     * @description: 创建订单
     * @author: Sakura
     * @date: 4/7/20
     * @param user:
     * @param goods:
     * @return: com.k2j.bargains.common.domain.OrderInfo
     **/
    OrderInfo createOrder(UserVo user, GoodsVo goods);
}
