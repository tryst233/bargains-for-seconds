package com.k2j.bargains.order.persistence;

import com.k2j.bargains.common.domain.BargainsOrder;
import com.k2j.bargains.common.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

/**
 * @className: OrderMapper
 * @description: bargains_order 表数据访问层
 * @author: Sakura
 * @date: 4/8/20
 **/
@Mapper
public interface OrderMapper {

    /**
     * @description: 通过用户id与商品id从订单列表中获取订单信息
     * @author: Sakura
     * @date: 4/8/20
     * @param userId: 用户id
     * @param goodsId: 商品id
     * @return: com.k2j.bargains.common.domain.BargainsOrder 秒杀订单信息
     **/
    @Select("SELECT * FROM bargains_order WHERE user_id=#{userId} AND goods_id=#{goodsId}")
    BargainsOrder getBargainsOrderByUserIdAndGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    /**
     * @description: 将订单信息插入 order_info 表中
     * @author: Sakura
     * @date: 4/8/20
     * @param orderInfo: 订单信息
     * @return: long 插入成功的订单信息id
     **/
    @Insert("INSERT INTO order_info (user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)"
            + "VALUES (#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel}, #{status}, #{createDate})")
    // 查询出插入订单信息的表id，并返回
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "SELECT last_insert_id()")
    long insert(OrderInfo orderInfo);

    /**
     * @description: 将秒杀订单信息插入到 bargains_order 表中
     * @author: Sakura
     * @date: 4/8/20
     * @param bargainsOrder: 秒杀订单
     * @return: void
     **/
    @Insert("INSERT INTO bargains_order(user_id, order_id, goods_id) VALUES (#{userId}, #{orderId}, #{goodsId})")
    void insertBargainsOrder(BargainsOrder bargainsOrder);

    /**
     * @description: 获取订单信息
     * @author: Sakura
     * @date: 4/8/20
     * @param orderId:
     * @return: com.k2j.bargains.common.domain.OrderInfo
     **/
    @Select("select * from order_info where id = #{orderId}")
    OrderInfo getOrderById(@Param("orderId") long orderId);
}
