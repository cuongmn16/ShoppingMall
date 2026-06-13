package com.example.shoppingMall.dto.request;

import com.example.shoppingMall.enums.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    String productName;
    String description;
    long soldQuantity;
    ProductStatus productStatus;
    List<ProductImagesRequest> productImages;


}
