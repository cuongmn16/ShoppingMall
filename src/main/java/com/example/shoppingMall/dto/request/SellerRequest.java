package com.example.shoppingMall.dto.request;

public class SellerRequest {
    private long userId;
    private String shopName;
    private String shopDescription;
    private String shopLogo;

    public SellerRequest() {
    }
    public SellerRequest(long userId, String shopName, String shopDescription, String shopLogo) {
        this.userId = userId;
        this.shopName = shopName;
        this.shopDescription = shopDescription;
        this.shopLogo = shopLogo;
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
}
