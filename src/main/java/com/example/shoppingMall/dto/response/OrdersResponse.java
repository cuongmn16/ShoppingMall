package com.example.shoppingMall.dto.response;

import com.example.shoppingMall.enums.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersResponse {
    private UUID orderId;
    private UUID userId;
    private Long shippingAddressId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private BigDecimal shippingFee;
    private BigDecimal discountAmount;
    private Timestamp createAt;
    private Timestamp updateAt;
    private List<OrderItemsResponse> orderItems;


}
