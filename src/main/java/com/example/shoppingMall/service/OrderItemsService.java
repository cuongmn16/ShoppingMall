package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.OrderItemsRequest;
import com.example.shoppingMall.dto.response.OrderItemsResponse;
import java.util.List;

public interface OrderItemsService {

    List<OrderItemsResponse> getItemsByOrderId(long orderId);

    OrderItemsResponse getItemById(long itemId);

    OrderItemsResponse createItem(OrderItemsRequest request);

    void updateItem(long itemId, OrderItemsRequest request);

    void deleteItem(long itemId);

}
