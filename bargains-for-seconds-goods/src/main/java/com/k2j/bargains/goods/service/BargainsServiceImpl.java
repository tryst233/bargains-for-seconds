package com.k2j.bargains.goods.service;

import com.k2j.bargains.common.api.bargains.BargainsServiceApi;
import com.k2j.bargains.common.api.cache.RedisServiceApi;
import com.k2j.bargains.common.api.cache.vo.BgKeyPrefix;
import com.k2j.bargains.common.api.cache.vo.GoodsKeyPrefix;
import com.k2j.bargains.common.api.goods.GoodsServiceApi;
import com.k2j.bargains.common.api.goods.vo.GoodsVo;
import com.k2j.bargains.common.api.order.OrderServiceApi;
import com.k2j.bargains.common.api.user.vo.UserVo;
import com.k2j.bargains.common.domain.BargainsOrder;
import com.k2j.bargains.common.domain.OrderInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @className: BargainsServiceImpl
 * @description: 秒杀服务接口实现
 * @author: Sakura
 * @date: 4/8/20
 **/
@Service(interfaceClass = BargainsServiceApi.class)
public class BargainsServiceImpl implements BargainsServiceApi {

    @Autowired
    GoodsServiceApi goodsService;

    @Reference(interfaceClass = OrderServiceApi.class)
    OrderServiceApi orderService;

    @Reference(interfaceClass = RedisServiceApi.class)
    RedisServiceApi redisService;

    /**
     * @description: 减库存，生成订单，实现秒杀操作核心业务
     * 秒杀操作由两步构成，不可分割，为一个事务
     * @author: Sakura
     * @date: 4/8/20
     * @param user: 秒杀商品的用户
     * @param goods: 秒杀的商品
     * @return: com.k2j.bargains.common.domain.OrderInfo
     **/
    @Transactional
    @Override
    public OrderInfo bargains(UserVo user, GoodsVo goods) {

        // 1. 减库存
        boolean success = goodsService.reduceStock(goods);
        if (!success) {
            setGoodsOver(goods.getId());
            return null;
        }
        // 2. 生成订单；向 order_info 表和 bargains_order 表中写入订单信息
        OrderInfo order = orderService.createOrder(user, goods);
        // 3. 更新缓存中的库存信息
        GoodsVo good = goodsService.getGoodsVoByGoodsId(goods.getId());
        redisService.set(GoodsKeyPrefix.GOODS_STOCK, "" + good.getId(), good.getStockCount());

        return order;
    }

    /**
     * @description: 设置秒杀商品数量为0
     * @author: Sakura
     * @date: 4/8/20
     * @param goodsId:
     * @return: void
     **/
    private void setGoodsOver(long goodsId) {
        redisService.set(BgKeyPrefix.GOODS_BG_OVER, "" + goodsId, true);
    }

    /**
     * @description: 获取秒杀结果
     * @author: Sakura
     * @date: 4/8/20
     * @param userId:
     * @param goodsId:
     * @return: long
     **/
    @Override
    public long getBargainsResult(Long userId, long goodsId) {

        BargainsOrder order = orderService.getBargainsOrderByUserIdAndGoodsId(userId, goodsId);
        if (order != null) {//秒杀成功
            return order.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(BgKeyPrefix.GOODS_BG_OVER, "" + goodsId);
    }
}
