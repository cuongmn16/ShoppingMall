package com.example.shoppingMall.dto.response;

import com.example.shoppingMall.enums.OrderStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class OrdersResponse {
    private long orderId;
    private long userId;
    private Long shippingAddressId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private BigDecimal shippingFee;
    private BigDecimal discountAmount;
    private Timestamp createAt;
    private Timestamp updateAt;
    private List<OrderItemsResponse> orderItems;

    public OrdersResponse() {}

    public OrdersResponse(long orderId, long userId, Long shippingAddressId,
                          OrderStatus status, BigDecimal totalAmount,
                          BigDecimal shippingFee, BigDecimal discountAmount,
                          Timestamp createAt, Timestamp updateAt, List<OrderItemsResponse> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.shippingAddressId = shippingAddressId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.shippingFee = shippingFee;
        this.discountAmount = discountAmount;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.orderItems = orderItems;
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

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public List<OrderItemsResponse> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemsResponse> orderItems) {
        this.orderItems = orderItems;
    }
}
