package com.example.shoppingMall.model;

import java.math.BigDecimal;

public class OrderItems {
    private long orderItemId;        // renamed from itemId
    private Long orderId;
    private Long productId;
    private Long variationId;
    private int quantity;
    private BigDecimal unitPrice;   // changed from double to BigDecimal
    private BigDecimal totalPrice;  // new field

    public OrderItems() {
    }

    public OrderItems(long orderItemId, Long orderId, Long productId, Long variationId,
                      int quantity, BigDecimal unitPrice, BigDecimal totalPrice) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.variationId = variationId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getVariationId() {
        return variationId;
    }

    public void setVariationId(Long variationId) {
        this.variationId = variationId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void getOrderItemId(long itemId) {
    }

}
