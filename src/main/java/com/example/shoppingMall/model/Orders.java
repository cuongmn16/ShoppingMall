    package com.example.shoppingMall.model;

    import com.example.shoppingMall.enums.OrderStatus;

    import java.math.BigDecimal;
    import java.time.LocalDate;
    import java.util.List;

    public class Orders {
        private long orderId;
        private long userId;
        private Long shippingAddressId;
        private BigDecimal totalAmount;
        private BigDecimal shippingFee;
        private BigDecimal discountAmount;
        private LocalDate dateTime;
        private List<OrderItems> orderItems;
        private OrderStatus status;

        public Orders() {
        }

        public Orders(long orderId, long userId, long shippingAddressId, BigDecimal totalAmount, BigDecimal shippingFee, BigDecimal discountAmount, LocalDate dateTime, List<OrderItems> orderItems) {
            this.orderId = orderId;
            this.userId = userId;
            this.shippingAddressId = shippingAddressId;
            this.totalAmount = totalAmount;
            this.shippingFee = shippingFee;
            this.discountAmount = discountAmount;
            this.dateTime = dateTime;
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

        public LocalDate getDateTime() {
            return dateTime;
        }

        public void setDateTime(LocalDate dateTime) {
            this.dateTime = dateTime;
        }

        public List<OrderItems> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<OrderItems> orderItems) {
            this.orderItems = orderItems;
        }

        public OrderStatus getStatus() {
            return status;
        }

        public void setStatus(OrderStatus status) {
            this.status = status;
        }

    }
