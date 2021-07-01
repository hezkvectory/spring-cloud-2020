package com.sharding.demo.pojo;

public class Order {
    //订单id
    private Long orderId;
    //下单的商品名称
    private String goodsName;

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
