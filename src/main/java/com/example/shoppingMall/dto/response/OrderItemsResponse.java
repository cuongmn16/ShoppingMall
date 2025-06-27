package com.example.shoppingMall.dto.response;

public class OrderItemsResponse {
    private Long variationId;
    private long productId;
    private int quantity;

    public OrderItemsResponse() {}

    public OrderItemsResponse(Long variationId, long productId, int quantity) {
        this.variationId = variationId;
        this.productId = productId;
        this.quantity = quantity;
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
