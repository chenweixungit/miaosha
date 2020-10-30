package com.miaoshaproject.service.model;

import java.math.BigDecimal;

public class OrderModel {
    // 订单id
    private String id;
    // 用户id
    private Integer userId;
    // 商品id
    private Integer itemId;
    // 商品数量
    private BigDecimal orderPrice;
    // 购买数量
    private Integer amount;
    // 购买金额
    private BigDecimal orderAmount;
    // 秒杀id，如果不为空，则表示该商品在秒杀活动中
    private Integer promoId;

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }
}
