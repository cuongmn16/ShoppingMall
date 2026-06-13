package com.example.shoppingMall.dto.request;

import com.example.shoppingMall.enums.AccountStatus;
import com.example.shoppingMall.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    long userId;
    String username;
    String email;
    String fullName;
    String phone;
    String avatarUrl;
    LocalDate dateOfBirth;
    AccountStatus accountStatus;
    Gender gender;
}
