package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.OrdersRequest;
import com.example.shoppingMall.dto.response.OrderItemsResponse;
import com.example.shoppingMall.dto.response.OrdersResponse;

import java.util.List;

public interface OrdersService {

    List<OrdersResponse> getAllOrders(int pageNumber, int pageSize);

    List<OrdersResponse> getAllOrdersByUserId(long userId, int pageNumber, int pageSize);

    OrdersResponse getOrderDetail(long orderId);

    OrdersResponse addOrder(OrdersRequest ordersRequest);

    OrdersResponse updateOrder(long orderId, OrdersRequest ordersRequest);
}
