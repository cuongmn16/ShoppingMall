package com.example.shoppingMall.mapper;


import com.example.shoppingMall.dto.request.OrderItemsRequest;
import com.example.shoppingMall.dto.response.OrderItemsResponse;
import com.example.shoppingMall.model.OrderItems;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemsMapper {

    OrderItems toOrderItems(OrderItemsRequest orderItemsRequest);

    OrderItemsResponse toOrderItemsResponse(OrderItems orderItems);
}
