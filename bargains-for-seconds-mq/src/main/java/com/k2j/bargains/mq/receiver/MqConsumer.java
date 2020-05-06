package com.k2j.bargains.mq.receiver;

import com.k2j.bargains.common.api.bargains.BargainsServiceApi;
import com.k2j.bargains.common.api.cache.RedisServiceApi;
import com.k2j.bargains.common.api.cache.vo.OrderKeyPrefix;
import com.k2j.bargains.common.api.goods.GoodsServiceApi;
import com.k2j.bargains.common.api.goods.vo.GoodsVo;
import com.k2j.bargains.common.api.mq.vo.BgMessage;
import com.k2j.bargains.common.api.order.OrderServiceApi;
import com.k2j.bargains.common.api.user.vo.UserVo;
import com.k2j.bargains.common.domain.BargainsOrder;
import com.k2j.bargains.mq.config.MQConfig;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @className: MqConsumer
 * @description: MQ消息接收者, 消费者
 * 消费者绑定在队列监听，即可以接收到队列中的消息
 * @author: Sakura
 * @date: 4/9/20
 **/
@Service
public class MqConsumer {

    private static Logger logger = LoggerFactory.getLogger(MqConsumer.class);

    @Reference(interfaceClass = GoodsServiceApi.class)
    GoodsServiceApi goodsService;

    @Reference(interfaceClass = OrderServiceApi.class)
    OrderServiceApi orderService;

    @Reference(interfaceClass = BargainsServiceApi.class)
    BargainsServiceApi bargainsService;

    @Reference(interfaceClass = RedisServiceApi.class)
    RedisServiceApi redisService;

    /**
     * @description: 处理收到的秒杀成功信息（核心业务实现）
     * @author: Sakura
     * @date: 4/9/20
     * @param message:
     * @return: void
     **/
    @RabbitListener(queues = MQConfig.BARGAINS_QUEUE)
    public void receiveBgInfo(BgMessage message) {
        logger.info("MQ receive a message: " + message);

        // 获取秒杀用户信息与商品id
        UserVo user = message.getUser();
        long goodsId = message.getGoodsId();

        // 获取商品的库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        Integer stockCount = goods.getStockCount();
        if (stockCount <= 0) {
            return;
        }

        // 判断是否已经秒杀到了（保证秒杀接口幂等性）
        BargainsOrder order = this.getBgOrderByUserIdAndGoodsId(user.getUuid(), goodsId);
        if (order != null) {
            return;
        }

        // 1.减库存 2.写入订单 3.写入秒杀订单
        bargainsService.bargains(user, goods);
    }

    /**
     * @description: 通过用户id与商品id从订单列表中获取订单信息，这个地方用了唯一索引（unique index）
     * 优化，不用每次都去数据库中读取秒杀订单信息，而是在第一次生成秒杀订单成功后，
     * 将订单存储在redis中，再次读取订单信息的时候就直接从redis中读取
     * @author: Sakura
     * @date: 4/9/20
     * @param userId:
     * @param goodsId:
     * @return: com.k2j.bargains.common.domain.BargainsOrder 秒杀订单信息
     **/
    private BargainsOrder getBgOrderByUserIdAndGoodsId(Long userId, long goodsId) {

        // 从redis中取缓存，减少数据库的访问
        BargainsOrder bargainsOrder = redisService.get(OrderKeyPrefix.BARGAINS_ORDER, ":" + userId + "_" + goodsId, BargainsOrder.class);
        if (bargainsOrder != null) {
            return bargainsOrder;
        }
        return orderService.getBargainsOrderByUserIdAndGoodsId(userId, goodsId);
    }
}
