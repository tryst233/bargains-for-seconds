package com.k2j.bargains.order.service;

import com.k2j.bargains.common.api.cache.RedisServiceApi;
import com.k2j.bargains.common.api.cache.vo.OrderKeyPrefix;
import com.k2j.bargains.common.api.goods.vo.GoodsVo;
import com.k2j.bargains.common.api.order.OrderServiceApi;
import com.k2j.bargains.common.api.user.vo.UserVo;
import com.k2j.bargains.common.domain.BargainsOrder;
import com.k2j.bargains.common.domain.OrderInfo;
import com.k2j.bargains.order.persistence.OrderMapper;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @className: OrderServiceImpl
 * @description: 订单服务实现
 * @author: Sakura
 * @date: 4/8/20
 **/
@Service(interfaceClass = OrderServiceApi.class)
public class OrderServiceImpl implements OrderServiceApi {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderMapper orderMapper;

    @Reference(interfaceClass = RedisServiceApi.class)
    RedisServiceApi redisService;

    @Override
    public OrderInfo getOrderById(long orderId) {
        return orderMapper.getOrderById(orderId);
    }

    @Override
    public BargainsOrder getBargainsOrderByUserIdAndGoodsId(long userId, long goodsId) {
        return orderMapper.getBargainsOrderByUserIdAndGoodsId(userId, goodsId);
    }

    @Override
    public OrderInfo createOrder(UserVo user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        BargainsOrder bargainsOrder = new BargainsOrder();

        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);// 订单中商品的数量
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getBargainsPrice());// 秒杀价格
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getUuid());

        // 将订单信息插入 order_info 表中
        long orderId = orderMapper.insert(orderInfo);
        logger.debug("将订单信息插入 order_info 表中: 记录为" + orderId);

        bargainsOrder.setGoodsId(goods.getId());
        bargainsOrder.setOrderId(orderInfo.getId());
        bargainsOrder.setUserId(user.getUuid());

        // 将秒杀订单插入 bargains_order 表中
        orderMapper.insertBargainsOrder(bargainsOrder);
        logger.debug("将秒杀订单插入 bargains_order 表中");

        // 将秒杀订单概要信息存储于redis中
        redisService.set(OrderKeyPrefix.BARGAINS_ORDER, ":" + user.getUuid() + "_" + goods.getId(), bargainsOrder);

        return orderInfo;
    }
}
