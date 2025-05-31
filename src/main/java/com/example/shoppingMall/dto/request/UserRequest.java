package com.example.shoppingMall.dto.request;

import com.example.shoppingMall.enums.AccountStatus;
import com.example.shoppingMall.enums.Gender;

import java.time.LocalDate;

public class UserRequest {
    private long userId;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private String avatarUrl;
    private LocalDate dateOfBirth;
    private AccountStatus accountStatus;
    private Gender gender;
}
