package com.example.shoppingMall.dto.request;

public class ProductImagesRequest {
    private long productId;
    private String imageUrl;
    private Boolean isPrimary;
    private long displayOrder;
    public ProductImagesRequest() {
    }
    public ProductImagesRequest(long productId, String imageUrl, Boolean isPrimary, long displayOrder) {
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.isPrimary = isPrimary;
        this.displayOrder = displayOrder;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }

    public long getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(long displayOrder) {
        this.displayOrder = displayOrder;
    }
}
