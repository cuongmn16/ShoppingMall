package com.example.shoppingMall.mapper;

import com.example.shoppingMall.dto.request.OrdersRequest;
import com.example.shoppingMall.dto.response.OrdersResponse;
import com.example.shoppingMall.model.Orders;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrdersMapper {

    Orders toOrders(OrdersRequest ordersRequest);

    OrdersResponse toOrdersResponse(Orders orders);
}
