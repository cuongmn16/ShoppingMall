package com.example.shoppingMall.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopCategoriesResponse {
    UUID categoryId;
    String categoryName;
    String iconUrl;
    Boolean isActive;
    Long parentId;


}
