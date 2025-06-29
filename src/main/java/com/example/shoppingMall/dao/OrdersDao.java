package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.OrderItems;
import com.example.shoppingMall.model.Orders;

import java.util.List;
import java.util.Optional;

public interface OrdersDao {

    List<Orders> getAllOrders(int pageNumber, int pageSize);

    List<Orders> getOrdersByUserId(long userId, int pageNumber, int pageSize);

    Optional<Orders> getOrderById(long orderId);

    Optional<Orders> getCartByUserId(long userId);

    Orders createOrder(Orders o);

    Orders updateOrder(long orderId, Orders o);

    boolean isOrderExists(long orderId);

    List<OrderItems> getOrderItemsByOrderId(long orderId);

}
