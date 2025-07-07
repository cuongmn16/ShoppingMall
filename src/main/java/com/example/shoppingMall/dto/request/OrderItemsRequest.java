package com.example.shoppingMall.dto.request;

public class OrderItemsRequest {
    private Long orderId;
    private Long variationId;
    private Long productId;
    private Integer quantity;

    public OrderItemsRequest() {}

    public OrderItemsRequest(Long orderId, Long variationId, Long productId, int quantity) {
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
