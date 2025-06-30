package com.example.shoppingMall.mapper;

import com.example.shoppingMall.dto.request.OrdersRequest;
import com.example.shoppingMall.dto.response.OrdersResponse;
import com.example.shoppingMall.model.Orders;
import org.mapstruct.Mapper;

import java.sql.Timestamp;
import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface OrdersMapper {

    default Timestamp map(LocalDate date) {
        return date != null ? Timestamp.valueOf(date.atStartOfDay()) : null;
    }

    default LocalDate map(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime().toLocalDate() : null;
    }

    Orders toOrders(OrdersRequest ordersRequest);

    OrdersResponse toOrdersResponse(Orders orders);
}
