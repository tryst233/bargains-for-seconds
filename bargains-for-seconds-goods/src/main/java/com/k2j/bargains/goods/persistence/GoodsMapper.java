package com.k2j.bargains.goods.persistence;

import com.k2j.bargains.common.api.goods.vo.GoodsVo;
import com.k2j.bargains.common.domain.BargainsGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @className: GoodsMapper
 * @description: goods 表的数据库访问层
 * @author: Sakura
 * @date: 4/8/20
 **/
@Mapper
public interface GoodsMapper {

    /**
     * @description: 查出商品信息（包含该商品的秒杀信息）
     * sql利用左外连接(LEFT JOIN...ON...)的方式查
     * @author: Sakura
     * @date: 4/8/20

     * @return: java.util.List<com.k2j.bargains.common.api.goods.vo.GoodsVo>
     **/
    @Select("SELECT g.*, mg.stock_count, mg.start_date, mg.end_date, mg.bargains_price FROM bargains_goods mg LEFT JOIN goods g ON mg.goods_id=g.id")
    List<GoodsVo> listGoodsVo();

    /**
     * @description: 通过商品的id查出商品的所有信息（包含该商品的秒杀信息）
     * @author: Sakura
     * @date: 4/8/20
     * @param goodsId:
     * @return: com.k2j.bargains.common.api.goods.vo.GoodsVo
     **/
    @Select("SELECT g.*, mg.stock_count, mg.start_date, mg.end_date, mg.bargains_price FROM bargains_goods mg LEFT JOIN goods g ON mg.goods_id=g.id where g.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") Long goodsId);

    /**
     * @description: 减少 bargains_goods 中的库存
     * 增加库存判断 stock_count>0, 使用乐观锁防止超卖
     * @author: Sakura
     * @date: 4/8/20
     * @param bargainsGoods:
     * @return: int
     **/
    @Update("UPDATE bargains_goods SET stock_count = stock_count-1 WHERE goods_id=#{goodsId} AND stock_count > 0")
    int reduceStack(BargainsGoods bargainsGoods);
}
