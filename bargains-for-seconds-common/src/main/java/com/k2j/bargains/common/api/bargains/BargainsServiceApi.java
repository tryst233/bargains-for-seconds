package com.k2j.bargains.common.api.bargains;

import com.k2j.bargains.common.api.goods.vo.GoodsVo;
import com.k2j.bargains.common.api.user.vo.UserVo;
import com.k2j.bargains.common.domain.OrderInfo;

/**
 * @className: BargainsServiceApi
 * @description: 秒杀服务接口
 * @author: Sakura
 * @date: 4/7/20
 **/
public interface BargainsServiceApi {

    /**
     * @description: 执行秒杀操作，包含以下两步：
     * 1. 从goods表中减库存
     * 2. 将生成的订单写入bargains_order表中
     * @author: Sakura
     * @date: 4/7/20
     * @param user: 秒杀商品的用户
     * @param goods: 秒杀的商品
     * @return: com.k2j.bargains.common.domain.OrderInfo 生成的订单信息
     **/
    OrderInfo bargains(UserVo user, GoodsVo goods);

    /**
     * @description: 获取秒杀结果
     * @author: Sakura
     * @date: 4/7/20
     * @param userId:
     * @param goodsId:
     * @return: long
     **/
    long getBargainsResult(Long userId, long goodsId);
}
