package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.OrderItems;

import java.util.List;
import java.util.Optional;

public interface OrderItemsDao {

    List<OrderItems> getItemsByOrderId(long orderId);

    Optional<OrderItems> getItemById(long itemId);

    OrderItems createItem(OrderItems item);

    void updateItem(long itemId, OrderItems item);

    void deleteItem(long itemId);

    boolean isItemExists(long itemId);

}
