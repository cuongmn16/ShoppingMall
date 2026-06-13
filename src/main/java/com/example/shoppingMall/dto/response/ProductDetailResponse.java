package com.example.shoppingMall.dto.response;

import com.example.shoppingMall.enums.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailResponse {
    UUID productId;
    UUID sellerId;
    UUID categoryId;
    String productName;
    String description;
    double price;
    double originalPrice;
    double discount;
    long stockQuantity;
    long soldQuantity;
    double rating;
    ProductStatus productStatus;
    String productImage;
    String shopName;
    String shopDescription;
    String shopLogo;
    List<ProductImagesResponse> productImages;


}
