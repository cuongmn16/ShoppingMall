package com.example.shoppingMall.dto.response;

public class ShopCategoriesResponse {
    private long categoryId;
    private String categoryName;
    private String iconUrl;
    private Boolean isActive;

    public ShopCategoriesResponse() {
    }
    public ShopCategoriesResponse(long categoryId, String categoryName, String iconUrl, Boolean isActive) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.iconUrl = iconUrl;
        this.isActive = isActive;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
