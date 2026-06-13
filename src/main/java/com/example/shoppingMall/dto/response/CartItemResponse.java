package com.example.shoppingMall.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    UUID cartItemId;
    UUID productId;
    String ProductName;
    BigDecimal price;
    Integer quantity;
    BigDecimal subtotal;

}
