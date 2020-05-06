package com.k2j.bargains.common.api.goods;

import com.k2j.bargains.common.api.goods.vo.GoodsVo;

import java.util.List;

/**
 * @className: GoodsServiceApi
 * @description: 商品服务接口
 * @author: Sakura
 * @date: 4/6/20
 **/
public interface GoodsServiceApi {

    /**
     * @description: 获取商品列表
     * @author: Sakura
     * @date: 4/6/20

     * @return: java.util.List<com.k2j.bargains.common.api.goods.vo.GoodsVo>
     **/
    List<GoodsVo> listGoodsVo();

    /**
     * @description: 通过商品的id查出商品的所有信息（包含该商品的秒杀信息）
     * @author: Sakura
     * @date: 4/6/20
     * @param goodsId:
     * @return: com.k2j.bargains.common.api.goods.vo.GoodsVo
     **/
    GoodsVo getGoodsVoByGoodsId(long goodsId);

    /**
     * @description: 通过商品的id查出商品的所有信息（包含该商品的秒杀信息）
     * @author: Sakura
     * @date: 4/6/20
     * @param goodsId:
     * @return: com.k2j.bargains.common.api.goods.vo.GoodsVo
     **/
    GoodsVo getGoodsVoByGoodsId(Long goodsId);

    /**
     * @description: order表减库存
     * @author: Sakura
     * @date: 4/6/20
     * @param goods:
     * @return: boolean
     **/
    boolean reduceStock(GoodsVo goods);
}
