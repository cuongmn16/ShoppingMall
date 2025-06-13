package com.example.shoppingMall.model;

import com.example.shoppingMall.enums.OrderStatus;

import java.time.LocalDate;

public class Orders {
    private long orderId;
    private long userId;
    private long shippingAddressId;
    private OrderStatus orderStatus;
    private double totalAmount;
    private double shippingFee;
    private double discountAmount;
    private LocalDate dateTime;

    public Orders() {
    }

    public Orders(long orderId, long userId, long shippingAddressId, OrderStatus orderStatus, double totalAmount, double shippingFee, double discountAmount, LocalDate dateTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.shippingAddressId = shippingAddressId;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.shippingFee = shippingFee;
        this.discountAmount = discountAmount;
        this.dateTime = dateTime;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }
}
