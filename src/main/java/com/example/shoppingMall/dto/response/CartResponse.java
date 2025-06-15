package com.example.shoppingMall.dto.response;

public class CartResponse {
    private Long cartId;
    private String username;
    private Long productId;
    private String productName;
    private double price;
    private Long variationId;
    private String variationName;
    private double priceAdjustment;
    private double finalPrice;
    private double totalAllPrice;
    private String imageUrl;
    private int quantity;

    public CartResponse() {
    }

    public CartResponse(Long cartId, String username, Long productId, String productName, double price,
                        Long variationId, String variationName, double priceAdjustment, double finalPrice, double totalAllPrice,
                        String imageUrl, int quantity) {
        this.cartId = cartId;
        this.username = username;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.variationId = variationId;
        this.variationName = variationName;
        this.priceAdjustment = priceAdjustment;
        this.finalPrice = finalPrice;
        this.totalAllPrice = totalAllPrice;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public double getTotalAllPrice() {
        return totalAllPrice;
    }

    public void setTotalAllPrice(double totalAllPrice) {
        this.totalAllPrice = totalAllPrice;
    }

    public Long getVariationId() {
        return variationId;
    }

    public void setVariationId(Long variationId) {
        this.variationId = variationId;
    }

    public String getVariationName() {
        return variationName;
    }

    public void setVariationName(String variationName) {
        this.variationName = variationName;
    }

    public double getPriceAdjustment() {
        return priceAdjustment;
    }

    public void setPriceAdjustment(double priceAdjustment) {
        this.priceAdjustment = priceAdjustment;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
