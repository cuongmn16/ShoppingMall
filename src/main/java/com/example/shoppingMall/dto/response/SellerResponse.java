package com.example.shoppingMall.dto.response;

import com.example.shoppingMall.model.Product;

import java.util.List;

public class SellerResponse {
    private long sellerId;
    private long userId;
    private String shopName;
    private String shopDescription;
    private String shopLogo;
    private Double rating;
    private long total_Products;
    private List<Product> products;

    public SellerResponse() {
    }

    public SellerResponse(long sellerId, long userId, String shopName, String shopDescription, String shopLogo, Double rating, long total_Products, List<Product> products) {
        this.sellerId = sellerId;
        this.userId = userId;
        this.shopName = shopName;
        this.shopDescription = shopDescription;
        this.shopLogo = shopLogo;
        this.rating = rating;
        this.total_Products = total_Products;
        this.products = products;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public long getTotal_Products() {
        return total_Products;
    }

    public void setTotal_Products(long total_Products) {
        this.total_Products = total_Products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
