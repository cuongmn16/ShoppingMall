package com.example.shoppingMall.model;

public class ProductVariations {
    private long variationId;
    private long productId;
    private String sku;
    private String variationName;
    private Double priceAdjustment;
    private long stockQuantity;
    private String imageUrl;

    public ProductVariations() {
    }

    public ProductVariations(long variationId, long productId, String sku, String variationName, Double priceAdjustment, long stockQuantity, String imageUrl) {
        this.variationId = variationId;
        this.productId = productId;
        this.sku = sku;
        this.variationName = variationName;
        this.priceAdjustment = priceAdjustment;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
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

    public String getVariationName() {
        return variationName;
    }

    public void setVariationName(String variationName) {
        this.variationName = variationName;
    }

    public Double getPriceAdjustment() {
        return priceAdjustment;
    }

    public void setPriceAdjustment(Double priceAdjustment) {
        this.priceAdjustment = priceAdjustment;
    }

    public long getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
