package com.example.shoppingMall.service;

import com.example.shoppingMall.dao.OrdersDao;
import com.example.shoppingMall.dto.request.OrdersRequest;
import com.example.shoppingMall.dto.response.OrderItemsResponse;
import com.example.shoppingMall.dto.response.OrdersResponse;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.exception.ErrorCode;
import com.example.shoppingMall.mapper.OrdersMapper;
import com.example.shoppingMall.model.Orders;
import com.example.shoppingMall.model.OrderItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemsService orderItemsService;   // Service để lấy danh sách OrderItems

    @Override
    public List<OrdersResponse> getAllOrders(int pageNumber, int pageSize) {;
        return ordersDao.getAllOrders(pageNumber, pageSize)
                .stream()
                .map(ordersMapper::toOrdersResponse)
                .toList();
    }

    // Lấy tất cả đơn hàng của một user (phân trang)
    @Override
    public List<OrdersResponse> getAllOrdersByUserId(long userId, int pageNumber,  int pageSize) {
        List<Orders> list = ordersDao.getOrdersByUserId(userId, pageNumber, pageSize);
        if (list == null) {
            list = new ArrayList<>(); // tránh null
        }
        System.out.println("==> Orders: " + list.size());
        for (Orders o : list) {
            System.out.println("Order ID: " + o.getOrderId() + ", status: " + o.getStatus());
        }
        return ordersDao.getOrdersByUserId(userId, pageNumber, pageSize)
                .stream()
                .map(ordersMapper::toOrdersResponse)
                .toList();
    }

    // Lấy chi tiết đơn hàng
    @Override
    public OrdersResponse getOrderDetail(long orderId) {
        Orders orderEntity = ordersDao.getOrderById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        OrdersResponse response = new OrdersResponse();
        response.setOrderId(orderEntity.getOrderId());
        response.setUserId(orderEntity.getUserId());
        response.setShippingAddressId(orderEntity.getShippingAddressId());
        response.setStatus(orderEntity.getStatus());
        response.setTotalAmount(orderEntity.getTotalAmount());
        response.setShippingFee(orderEntity.getShippingFee());
        response.setDiscountAmount(orderEntity.getDiscountAmount());

        // Nếu bạn muốn hiển thị thời gian kiểu Timestamp
        response.setCreateAt(Timestamp.valueOf(orderEntity.getCreateAt().atStartOfDay()));
        response.setUpdateAt(Timestamp.valueOf(orderEntity.getCreateAt().atStartOfDay()));

        // Gọi OrderItems
        List<OrderItems> items = ordersDao.getOrderItemsByOrderId(orderId);
        List<OrderItemsResponse> itemResponses = items.stream().map(item -> {
            OrderItemsResponse res = new OrderItemsResponse();
            res.setVariationId(item.getVariationId());
            res.setProductId(item.getProductId());
            res.setQuantity(item.getQuantity());
            return res;
        }).toList();

        response.setOrderItems(itemResponses);
        return response;
    }

    // Thêm đơn hàng mới
    @Override
    public OrdersResponse addOrder(OrdersRequest req) {

        Orders entity = ordersMapper.toOrders(req);
        Orders saved  = ordersDao.createOrder(entity);

        return ordersMapper.toOrdersResponse(saved);
    }

    // Cập nhật thông tin đơn hàng
    @Override
    public OrdersResponse updateOrder(long orderId, OrdersRequest req) {

        Orders entity = ordersMapper.toOrders(req);
        Orders updated = ordersDao.updateOrder(orderId, entity);

        if (updated == null) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }
        return ordersMapper.toOrdersResponse(updated);
    }
}
