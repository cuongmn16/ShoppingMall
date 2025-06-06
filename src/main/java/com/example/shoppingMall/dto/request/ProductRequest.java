package com.example.shoppingMall.dto.request;

import com.example.shoppingMall.enums.ProductStatus;

public class ProductRequest {
    private long sellerId;
    private long categoryId;
    private String productName;
    private double price;
    private double originalPrice;
    private double discount;
    private long stockQuantity;
    private long soldQuantity;
    private ProductStatus productStatus;
    private String productImage;

    public ProductRequest() {
    }

    public ProductRequest(long sellerId, long categoryId, String productName, double price,double discount, double originalPrice, long stockQuantity, long soldQuantity, ProductStatus productStatus, String productImage) {
        this.sellerId = sellerId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.price = price;
        this.discount = discount;
        this.originalPrice = originalPrice;
        this.stockQuantity = stockQuantity;
        this.soldQuantity = soldQuantity;
        this.productStatus = productStatus;
        this.productImage = productImage;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public long getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public long getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(long soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
