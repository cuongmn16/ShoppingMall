package com.example.shoppingMall.dto.request;

public class ShopCategoriesRequest {
    private String categoryName;
    private String iconUrl;
    private Boolean isActive;
    private Long parentId;

    public ShopCategoriesRequest() {
    }
    public ShopCategoriesRequest(String categoryName, String iconUrl, Boolean isActive, Long parentId) {
        this.categoryName = categoryName;
        this.iconUrl = iconUrl;
        this.isActive = isActive;
        this.parentId = parentId;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }


}
