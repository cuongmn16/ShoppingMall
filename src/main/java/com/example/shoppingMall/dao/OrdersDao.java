package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.Orders;

public interface OrdersDao {

    Orders createOrder(String username, Orders orders);
}
