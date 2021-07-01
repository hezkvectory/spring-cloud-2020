package com.sharding.demo.pojo;

public class Goods {
    //商品id
    Long goodsId;
    //商品名称
    private String goodsName;
    //库存
    int stock;

    public Long getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    //tostring
    public String toString() {
        return " Goods:goodsId=" + goodsId + " goodsName=" + goodsName + " stock=" + stock;
    }
}
