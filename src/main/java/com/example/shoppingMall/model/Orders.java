    package com.example.shoppingMall.model;

    import com.example.shoppingMall.enums.OrderStatus;
    import com.fasterxml.jackson.annotation.JsonInclude;
    import org.springframework.cglib.core.Local;

    import java.math.BigDecimal;
    import java.time.LocalDate;
    import java.util.List;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class Orders {
        private long orderId;
        private long userId;
        private Long shippingAddressId;
        private BigDecimal totalAmount;
        private BigDecimal shippingFee;
        private BigDecimal discountAmount;
        private LocalDate createAt;
        private LocalDate updateAt;
        private List<OrderItems> orderItems;
        private OrderStatus status;
        private Addresses shippingAddress;

        public Orders() {
        }

        public Orders(long orderId, long userId, Long shippingAddressId, BigDecimal totalAmount, BigDecimal shippingFee, BigDecimal discountAmount, LocalDate createAt, LocalDate updateAt, List<OrderItems> orderItems, OrderStatus status, Addresses shippingAddress) {
            this.orderId = orderId;
            this.userId = userId;
            this.shippingAddressId = shippingAddressId;
            this.totalAmount = totalAmount;
            this.shippingFee = shippingFee;
            this.discountAmount = discountAmount;
            this.createAt = createAt;
            this.updateAt = updateAt;
            this.orderItems = orderItems;
            this.status = status;
            this.shippingAddress = shippingAddress;
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

        public LocalDate getCreateAt() {
            return createAt;
        }

        public void setCreateAt(LocalDate createAt) {
            this.createAt = createAt;
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

        public Addresses getShippingAddress() {
            return shippingAddress;
        }

        public void setShippingAddress(Addresses shippingAddress) {
            this.shippingAddress = shippingAddress;
        }

        public LocalDate getUpdateAt() {
            return updateAt;
        }

        public void setUpdateAt(LocalDate updateAt) {
            this.updateAt = updateAt;
        }
    }
