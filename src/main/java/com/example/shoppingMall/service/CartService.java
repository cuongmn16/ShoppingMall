package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.CartRequest;
import com.example.shoppingMall.dto.response.CartResponse;

import java.util.List;

public interface CartService {
    List<CartResponse> getAllCarts(String username);

    CartResponse addToCart(String username,CartRequest cartRequest);

    void deleteCart(long cartId);
}
