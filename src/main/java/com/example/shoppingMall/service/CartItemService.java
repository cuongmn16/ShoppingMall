package com.example.shoppingMall.service;

import com.example.shoppingMall.entity.CartItem;

import java.util.List;

public interface CartItemService {
    public List<CartItem> getCartItemsByUserId(String userId);
}
