package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.UserCreationRequest;
import com.example.shoppingMall.dto.response.UserResponse;
import com.example.shoppingMall.model.User;

import java.util.List;

public interface UserService {

    public List<UserResponse> getAllUsers();
    public UserResponse createUser(UserCreationRequest userCreationRequest);
    public UserResponse getUserById(long userId);
    public void deleteUser(long userId);
}
