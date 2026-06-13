package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.response.CartResponse;
import com.example.shoppingMall.entity.Cart;

import java.util.UUID;

public interface CartService {
    public CartResponse getCart( UUID userId);
}
