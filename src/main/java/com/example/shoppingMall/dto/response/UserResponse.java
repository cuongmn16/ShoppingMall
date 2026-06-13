package com.example.shoppingMall.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    UUID userId;
    String username;
    String email;
    String fullName;
    String phone;
    String avatarUrl;
    String dateOfBirth;
    String accountStatus;
    String gender;
    Set<RoleResponse> roles;


}
