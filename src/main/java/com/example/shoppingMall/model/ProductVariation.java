package com.example.shoppingMall.model;

public class ProductVariation {
    private long variationId;
    private long productId;
    private String sku;
    private long quantity;

    public ProductVariation() {
    }

    public ProductVariation(long variationId, long productId, String sku, long quantity) {
        this.variationId = variationId;
        this.productId = productId;
        this.sku = sku;
        this.quantity = quantity;
    }

    public long getVariationId() {
        return variationId;
    }

    public void setVariationId(long variationId) {
        this.variationId = variationId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
