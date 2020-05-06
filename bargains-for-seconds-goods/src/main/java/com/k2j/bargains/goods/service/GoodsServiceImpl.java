package com.k2j.bargains.goods.service;

import com.k2j.bargains.common.api.goods.GoodsServiceApi;
import com.k2j.bargains.common.api.goods.vo.GoodsVo;
import com.k2j.bargains.common.domain.BargainsGoods;
import com.k2j.bargains.goods.persistence.GoodsMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @className: GoodsServiceImpl
 * @description: 商品服务接口实现
 * @author: Sakura
 * @date: 4/8/20
 **/
@Service(interfaceClass = GoodsServiceApi.class)
public class GoodsServiceImpl implements GoodsServiceApi {

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public List<GoodsVo> listGoodsVo() {
        return goodsMapper.listGoodsVo();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsMapper.getGoodsVoByGoodsId(goodsId);
    }

    /**
     * @description: 通过商品的id查出商品的所有信息（包含该商品的秒杀信息）
     * @author: Sakura
     * @date: 4/8/20
     * @param goodsId:
     * @return: com.k2j.bargains.common.api.goods.vo.GoodsVo
     **/
    @Override
    public GoodsVo getGoodsVoByGoodsId(Long goodsId) {
        return goodsMapper.getGoodsVoByGoodsId(goodsId);
    }

    /**
     * @description: 减库存
     * @author: Sakura
     * @date: 4/8/20
     * @param goods:
     * @return: boolean
     **/
    @Override
    public boolean reduceStock(GoodsVo goods) {
        BargainsGoods bargainsGoods = new BargainsGoods();
        // 秒杀商品的id和商品的id是一样的
        bargainsGoods.setGoodsId(goods.getId());
        int ret = goodsMapper.reduceStack(bargainsGoods);
        return ret > 0;
    }
}
