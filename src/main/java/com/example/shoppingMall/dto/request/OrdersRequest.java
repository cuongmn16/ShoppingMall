package com.example.shoppingMall.dto.request;

import com.example.shoppingMall.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public class OrdersRequest {
    private long userId;
    private Long shippingAddressId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private BigDecimal shippingFee;
    private BigDecimal discountAmount;
    private List<OrderItemsRequest> orderItems;

    public OrdersRequest() {}

    public OrdersRequest(long userId, Long shippingAddressId, OrderStatus status,
                        BigDecimal totalAmount, BigDecimal shippingFee,
                        BigDecimal discountAmount, List<OrderItemsRequest> orderItems) {
        this.userId = userId;
        this.shippingAddressId = shippingAddressId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.shippingFee = shippingFee;
        this.discountAmount = discountAmount;
        this.orderItems = orderItems;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public List<OrderItemsRequest> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemsRequest> orderItems) {
        this.orderItems = orderItems;
    }
}
