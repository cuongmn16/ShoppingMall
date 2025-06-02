package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.UserUpdateRequest;
import com.example.shoppingMall.dto.request.UserCreationRequest;
import com.example.shoppingMall.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();

    UserResponse createUser(UserCreationRequest userCreationRequest);

    UserResponse updateUser(long userId, UserUpdateRequest updateUserRequest);

    UserResponse getMyInfo();

    UserResponse getUserById(long userId);

    void deleteUser(long userId);
}
