package com.example.shoppingMall.service;

import com.example.shoppingMall.entity.CartItem;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class CartItemServiceImpl implements CartItemService {
    @Override
    public List<CartItem> getCartItemsByUserId(String userId) {

        return List.of();
    }
}
