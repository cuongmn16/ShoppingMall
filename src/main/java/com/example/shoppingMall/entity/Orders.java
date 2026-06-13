package com.example.shoppingMall.entity;

import com.example.shoppingMall.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    UUID orderId;
    BigDecimal totalAmount;
    BigDecimal shippingFee;
    BigDecimal discountAmount;
    LocalDate createAt;
    LocalDate updateAt;
    OrderStatus status;
    @ManyToOne
    @JoinColumn(name = "address_id")
    Addresses shippingAddress;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;


}
