package com.example.shoppingMall.service;

import com.example.shoppingMall.dao.OrderItemsDao;
import com.example.shoppingMall.dto.request.OrderItemsRequest;
import com.example.shoppingMall.dto.response.OrderItemsResponse;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.exception.ErrorCode;
import com.example.shoppingMall.mapper.OrderItemsMapper;
import com.example.shoppingMall.model.OrderItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemsServiceImpl implements OrderItemsService {

    @Autowired
    private OrderItemsDao orderItemsDao;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Override
    public List<OrderItemsResponse> getItemsByOrderId(long orderId) {
        return orderItemsDao.getItemsByOrderId(orderId)
                .stream()
                .map(orderItemsMapper::toOrderItemsResponse)
                .toList();
    }

    @Override
    public OrderItemsResponse getItemById(long itemId) {
        return orderItemsDao.getItemById(itemId)
                .map(orderItemsMapper::toOrderItemsResponse)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_ITEM_NOT_FOUND));
    }

    @Override
    public OrderItemsResponse createItem(OrderItemsRequest request) {
        OrderItems orderItem = orderItemsMapper.toOrderItems(request);
        System.out.println("Mapped orderId: " + orderItem.getOrderId());
        OrderItems saved = orderItemsDao.createItem(orderItem);
        return orderItemsMapper.toOrderItemsResponse(saved);
    }

    @Override
    public OrderItemsResponse updateItem(long itemId, OrderItemsRequest request) {
        if (!orderItemsDao.getItemById(itemId).isPresent()) {
            throw new AppException(ErrorCode.ORDER_ITEM_NOT_FOUND);
        }

        OrderItems updatedItem = orderItemsMapper.toOrderItems(request);
        orderItemsDao.updateItem(itemId, updatedItem);
        return null;
    }

    @Override
    public void deleteItem(long itemId) {
        if (!orderItemsDao.getItemById(itemId).isPresent()) {
            throw new AppException(ErrorCode.ORDER_ITEM_NOT_FOUND);
        }

        orderItemsDao.deleteItem(itemId);
    }
}
