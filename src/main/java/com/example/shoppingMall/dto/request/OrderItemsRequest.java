package com.example.shoppingMall.dto.request;

public class OrderItemsRequest {
    private Long orderId;
    private Long variationId;
    private long productId;
    private int quantity;

    public OrderItemsRequest() {}

    public OrderItemsRequest(Long orderId, Long variationId, long productId, int quantity) {
        this.orderId = orderId;
        this.variationId = variationId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getVariationId() {
        return variationId;
    }

    public void setVariationId(Long variationId) {
        this.variationId = variationId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
