package com.example.shoppingMall.service.impl;

import com.example.shoppingMall.dto.response.CartResponse;
import com.example.shoppingMall.entity.Cart;
import com.example.shoppingMall.repository.CartRepository;
import com.example.shoppingMall.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class CartServiceImpl implements CartService {
    CartRepository cartRepository;

    @Override
    public CartResponse getCart( UUID userId) {
//        var cart = cartRepository.findByUserId(userId);
        return null;
    }
}
