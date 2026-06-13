package com.example.shoppingMall.entity;

import com.example.shoppingMall.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;
@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID productId;
    private String productName;
    private String description;
    private int sold_quantity;
    private double rating;
    private ProductStatus productStatus;
    private String productImage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ShopCategories category;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "product")
    private List<ProductImages> productImages;
}
