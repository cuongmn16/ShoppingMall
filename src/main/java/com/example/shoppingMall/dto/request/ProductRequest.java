package com.example.shoppingMall.dto.request;

import com.example.shoppingMall.enums.ProductStatus;
import com.example.shoppingMall.model.ProductImages;
import com.example.shoppingMall.model.ProductVariation;

import java.util.List;
import java.util.Map;

public class ProductRequest {
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
    private List<ProductImagesRequest> productImages;
    private List<ProductVariationsRequest> variations;
    private Map<String, List<String>> optionInputs;

    public ProductRequest() {
    }

    public ProductRequest(long sellerId, long categoryId, String productName, String description, double price, double originalPrice, double discount, long stockQuantity, long soldQuantity, double rating, ProductStatus productStatus, String productImage, List<ProductImagesRequest> productImages, List<ProductVariationsRequest> variations, Map<String, List<String>> optionInputs) {
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
        this.productImages = productImages;
        this.variations = variations;
        this.optionInputs = optionInputs;
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

    public List<ProductImagesRequest> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImagesRequest> productImages) {
        this.productImages = productImages;
    }

    public List<ProductVariationsRequest> getVariations() {
        return variations;
    }

    public void setVariations(List<ProductVariationsRequest> variations) {
        this.variations = variations;
    }

    public Map<String, List<String>> getOptionInputs() {
        return optionInputs;
    }

    public void setOptionInputs(Map<String, List<String>> optionInputs) {
        this.optionInputs = optionInputs;
    }
}
