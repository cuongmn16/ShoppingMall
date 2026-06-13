package com.example.shoppingMall.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    UUID cartId;
    List<CartItemResponse> items;
    Integer totalItems;
    BigDecimal totalAmount;

}
