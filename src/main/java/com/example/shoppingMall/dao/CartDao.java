package com.example.shoppingMall.dao;

import com.example.shoppingMall.dto.response.CartResponse;
import com.example.shoppingMall.model.Cart;

import java.util.List;

public interface CartDao {
    List<CartResponse> getAllCarts(String username);

    Cart addToCart( String username, Cart cart);

    void deleteCart(long cartId);



}
