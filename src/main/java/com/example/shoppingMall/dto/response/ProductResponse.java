    package com.example.shoppingMall.dto.response;

    import com.example.shoppingMall.enums.ProductStatus;
    import lombok.*;
    import lombok.experimental.FieldDefaults;

    import java.util.UUID;

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class ProductResponse {
        UUID productId;
        UUID sellerId;
        UUID categoryId;
        String productName;
        String description;
        double price;
        double originalPrice;
        Double discount;
        long stockQuantity;
        long soldQuantity;
        double rating;
        ProductStatus productStatus;
        String productImage;


    }
