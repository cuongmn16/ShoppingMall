package com.example.shoppingMall.model;

import java.io.Serializable;
import java.util.List;

public class ShopCategories implements Serializable {
    private long categoryId;
    private String categoryName;
    private String iconUrl;
    private Boolean isActive;
    private Long parentId;
    private List<Product> products;
    public ShopCategories() {
    }

    public ShopCategories(long categoryId, String categoryName, String iconUrl, Boolean isActive, long parentId, List<Product> products) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.iconUrl = iconUrl;
        this.isActive = isActive;
        this.products = products;
        this.parentId = parentId;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
