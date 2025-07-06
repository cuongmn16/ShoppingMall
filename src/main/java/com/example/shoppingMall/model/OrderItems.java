package com.example.shoppingMall.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItems {
    private long itemId;
    private Long orderId;
    private Long productId;
    private Long variationId;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private List<VariationOption> variationOptions  = new ArrayList<>();
    private Product product;
    private ProductVariation productVariation;

    public OrderItems() {
    }

    public OrderItems(long itemId, Long orderId, Long productId, Long variationId, int quantity, BigDecimal unitPrice, BigDecimal totalPrice, List<VariationOption> variationOptions, Product product, ProductVariation productVariation) {
        this.itemId = itemId;
        this.orderId = orderId;
        this.productId = productId;
        this.variationId = variationId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.variationOptions = variationOptions;
        this.product = product;
        this.productVariation = productVariation;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getVariationId() {
        return variationId;
    }

    public void setVariationId(Long variationId) {
        this.variationId = variationId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getItemId() {
        return itemId;
    }
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public List<VariationOption> getVariationOptions() {
        return variationOptions;
    }

    public void setVariationOptions(List<VariationOption> variationOptions) {
        this.variationOptions = variationOptions;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }
}
