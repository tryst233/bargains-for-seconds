package com.k2j.bargains.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @className: BargainsGoods
 * @description: bargains_goods 秒杀商品信息
 * @author: Sakura
 * @date: 4/7/20
 **/
public class BargainsGoods implements Serializable {

    private Long id;
    private Long goodsId;
    private Double bargainsPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

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
