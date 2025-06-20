package com.example.shoppingMall.dto.request;

import java.util.List;

public class ProductVariationsRequest {
    private long productId;
    private String sku;
    private double price;
    private long quantity;
    private List<String> optionInputs;

    public ProductVariationsRequest() {
    }

    public ProductVariationsRequest(long productId, String sku, double price, long quantity, List<String> optionInputs) {
        this.productId = productId;
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
        this.optionInputs = optionInputs;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public List<String> getOptionInputs() {
        return optionInputs;
    }

    public void setOptionInputs(List<String> optionInputs) {
        this.optionInputs = optionInputs;
    }
}
