package com.example.shoppingMall.dto.request;

public class CartRequest {
    private long productId;
    private long variationId;
    private int quantity;

    public CartRequest() {
    }

    public CartRequest(long productId, long variationId, int quantity) {
        this.productId = productId;
        this.variationId = variationId;
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getVariationId() {
        return variationId;
    }

    public void setVariationId(long variationId) {
        this.variationId = variationId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
