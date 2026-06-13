package com.example.shoppingMall.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "ProductImages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImages {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    UUID imageId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
    String imageUrl;
    Boolean isPrimary;
    long displayOrder;

}
