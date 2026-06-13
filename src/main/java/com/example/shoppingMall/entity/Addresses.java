package com.example.shoppingMall.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Addresses {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    @Id
    private long addressId;
    private long userId;
    private String recipientName;
    private String phone;
    private String province;
    private String district;
    private String ward;
    private String detail_address;
    private Boolean isDefault;

}
