package com.example.shoppingMall.dao;

import com.example.shoppingMall.dto.response.OrderItemsResponse;
import com.example.shoppingMall.dto.response.OrdersResponse;
import com.example.shoppingMall.model.OrderItems;
import com.example.shoppingMall.model.Orders;

import java.util.List;
import java.util.Optional;

public interface OrdersDao {

    List<Orders> getAllOrders(int pageNumber, int pageSize);

    Optional<Orders> getOrderById(long orderId);

    Orders createOrder(Orders o);

    Orders updateOrder(long orderId, Orders o);

    boolean isOrderExists(long orderId);

    List<OrderItems> getOrderItemsByOrderId(long orderId);

    List<Orders> findByUserUsername(String username, int limit, int offset);

    List<OrderItemsResponse> getItemDetailsWithProductInfo(long orderId);

}
