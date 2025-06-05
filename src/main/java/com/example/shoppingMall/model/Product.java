package com.example.shoppingMall.model;

import com.example.shoppingMall.enums.ProductStatus;

import java.util.List;

public class Product {
    private long productId;
    private long sellerId;
    private long categoryId;
    private String productName;
    private String description;
    private double price;
    private double originalPrice;
    private Double discount;
    private long stockQuantity;
    private long soldQuantity;
    private double rating;
    private ProductStatus productStatus;
    private List<ProductImages> productImages;
    private List<ProductAttributes> productAttributes;
    private List<ProductVariations> productVariations;

    public Product() {
    }

    public Product(long productId, long sellerId, long categoryId, String productName, String description, double price, double originalPrice, Double discount, long stockQuantity, long soldQuantity, double rating, ProductStatus productStatus, List<ProductImages> productImages, List<ProductAttributes> productAttributes, List<ProductVariations> productVariations) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.originalPrice = originalPrice;
        this.discount = discount;
        this.stockQuantity = stockQuantity;
        this.soldQuantity = soldQuantity;
        this.rating = rating;
        this.productStatus = productStatus;
        this.productImages = productImages;
        this.productAttributes = productAttributes;
        this.productVariations = productVariations;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public List<ProductImages> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImages> productImages) {
        this.productImages = productImages;
    }

    public List<ProductAttributes> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(List<ProductAttributes> productAttributes) {
        this.productAttributes = productAttributes;
    }

    public List<ProductVariations> getProductVariations() {
        return productVariations;
    }

    public void setProductVariations(List<ProductVariations> productVariations) {
        this.productVariations = productVariations;
    }
}
