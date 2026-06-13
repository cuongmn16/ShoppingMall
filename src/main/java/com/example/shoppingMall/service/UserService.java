package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.UserUpdateRequest;
import com.example.shoppingMall.dto.request.UserCreationRequest;
import com.example.shoppingMall.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserResponse> getAllUsers();

    UserResponse createUser(UserCreationRequest userCreationRequest);

    UserResponse updateUser(UUID userId, UserUpdateRequest updateUserRequest);

    UserResponse getMyInfo();

    UserResponse getUserById(UUID userId);

    void deleteUser(UUID userId);
}
