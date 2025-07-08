package com.example.shoppingMall.dto.response;

import com.example.shoppingMall.enums.ProductStatus;


import java.util.List;

public class ProductDetailResponse {
    private long productId;
    private long sellerId;
    private long categoryId;
    private String productName;
    private String description;
    private double price;
    private double originalPrice;
    private double discount;
    private long stockQuantity;
    private long soldQuantity;
    private double rating;
    private ProductStatus productStatus;
    private String productImage;
    private String shopName;
    private String shopDescription;
    private String shopLogo;
    private List<ProductImagesResponse> productImages;
    private List<ProductVariationResponse> variations;

    public ProductDetailResponse() {
    }

    public ProductDetailResponse(long productId, long sellerId, long categoryId, String productName, String description, double price, double originalPrice, double discount, long stockQuantity, long soldQuantity, double rating, ProductStatus productStatus, String productImage, String shopName, String shopDescription, String shopLogo, List<ProductImagesResponse> productImages, List<ProductVariationResponse> variations) {
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
        this.productImage = productImage;
        this.shopName = shopName;
        this.shopDescription = shopDescription;
        this.shopLogo = shopLogo;
        this.productImages = productImages;
        this.variations = variations;
    }

    public List<ProductVariationResponse> getVariations() {
        return variations;
    }

    public void setVariations(List<ProductVariationResponse> variations) {
        this.variations = variations;
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public List<ProductImagesResponse> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImagesResponse> productImages) {
        this.productImages = productImages;
    }


}
