package com.example.shoppingMall.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ShopCategories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID categoryId;
    UUID userId;
    String categoryName;
    String iconUrl;
    Boolean isActive;
    @OneToMany(
            mappedBy = "category",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Product> products;

}
