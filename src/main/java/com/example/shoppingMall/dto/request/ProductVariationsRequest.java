package com.example.shoppingMall.dto.request;

public class ProductVariationsRequest {
    private long productId;
    private String variationName;
    private Double priceAdjustment;
    private long stockQuantity;
    private String imageUrl;

    public ProductVariationsRequest() {
    }

    public ProductVariationsRequest(long productId, String variationName, Double priceAdjustment, long stockQuantity, String imageUrl) {
        this.productId = productId;
        this.variationName = variationName;
        this.priceAdjustment = priceAdjustment;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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
