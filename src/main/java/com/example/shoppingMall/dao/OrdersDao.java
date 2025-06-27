package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.Orders;

import java.util.List;
import java.util.Optional;

public interface OrdersDao {

    List<Orders> getAllOrders();

    Optional<Orders> getOrderById(long orderId);

    List<Orders> getOrdersByUserId(long userId);

    Optional<Orders> getCartByUserId(long userId);      // status = CART

    Orders createOrder(Orders orders);

    void updateOrder(long orderId, Orders orders);

    void deleteOrder(long orderId);

    boolean isOrderExists(long orderId);
}
