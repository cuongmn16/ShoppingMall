package com.example.shoppingMall.dto.response;

public class CartResponse {
    private long cartId;
    private String username;
    private long productId;
    private long variationId;
    private int quantity;

    public CartResponse() {
    }

    public CartResponse(long cartId, String username, long productId, long variationId, int quantity) {
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
