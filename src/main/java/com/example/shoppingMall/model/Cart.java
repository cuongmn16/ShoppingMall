package com.example.shoppingMall.model;

public class Cart {
    private long cartId;
    private String username;
    private long productId;
    private long variationId;
    private int quantity;

    public Cart() {
    }

    public Cart(long cartId, String username, long productId, long variationId, int quantity) {
        this.cartId = cartId;
        this.username = username;
        this.productId = productId;
        this.variationId = variationId;
        this.quantity = quantity;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getVariationId() {
        return variationId;
    }

    public void setVariationId(long variationId) {
        this.variationId = variationId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
