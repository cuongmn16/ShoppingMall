package com.example.shoppingMall.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPrincipal {
    private Long userId;
    private String email;
}
