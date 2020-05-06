package com.k2j.bargains.common.api.goods.vo;

import com.k2j.bargains.common.domain.Goods;

import java.io.Serializable;
import java.util.Date;

/**
 * @className: GoodsVo
 * @description: 商品信息（并且包含商品的秒杀信息）
 * 商品信息和商品的秒杀信息是存储在两个表中的（goods 和 bargains_goods）
 * @author: Sakura
 * @date: 4/6/20
 **/
public class GoodsVo extends Goods implements Serializable {

    private Double bargainsPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;

    public Double getBargainsPrice() {
        return bargainsPrice;
    }

    public void setBargainsPrice(Double bargainsPrice) {
        this.bargainsPrice = bargainsPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
