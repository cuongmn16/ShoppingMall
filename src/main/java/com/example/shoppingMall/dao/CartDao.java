package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.Cart;

import java.util.List;

public interface CartDao {
    List<Cart> getAllCarts(long userId);

    Cart addToCart( String username, Cart cart);

}
