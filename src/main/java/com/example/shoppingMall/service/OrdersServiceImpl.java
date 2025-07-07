package com.example.shoppingMall.service;

import com.example.shoppingMall.dao.OrdersDao;
import com.example.shoppingMall.dto.request.OrdersRequest;
import com.example.shoppingMall.dto.response.OrderItemsResponse;
import com.example.shoppingMall.dto.response.OrdersResponse;
import com.example.shoppingMall.dto.response.ProductDetailResponse;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.exception.ErrorCode;
import com.example.shoppingMall.mapper.OrderItemsMapper;
import com.example.shoppingMall.mapper.OrdersMapper;
import com.example.shoppingMall.model.OrderItems;
import com.example.shoppingMall.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private ProductService productService;

    @Override
    public List<OrdersResponse> getAllOrders(int pageNumber, int pageSize) {
        return ordersDao.getAllOrders(pageNumber, pageSize)
                .stream()
                .map(ordersMapper::toOrdersResponse)
                .toList();
    }

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

        response.setCreateAt(Timestamp.valueOf(orderEntity.getCreateAt().atStartOfDay()));
        response.setUpdateAt(Timestamp.valueOf(orderEntity.getCreateAt().atStartOfDay()));

        // Gọi OrderItems
        List<OrderItems> items = ordersDao.getOrderItemsByOrderId(orderId);
        List<OrderItemsResponse> itemResponses = new ArrayList<>();

        for (OrderItems item : items) {
            OrderItemsResponse res = new OrderItemsResponse();
            res.setItemId(item.getItemId());
            res.setOrderId(item.getOrderId());
            res.setProductId(item.getProductId());
            res.setVariationId(item.getVariationId());
            res.setQuantity(item.getQuantity());
            res.setUnitPrice(item.getUnitPrice());
            res.setTotalPrice(item.getTotalPrice());

            // Gọi productService để lấy productName và sku
            ProductDetailResponse product = productService.getProductDetail(item.getProductId());
            res.setProductName(product.getProductName());
            res.setSku(product.getSku());

            itemResponses.add(res);
        }

        response.setOrderItems(itemResponses);
        return response;
    }

    @Override
    public OrdersResponse addOrder(OrdersRequest req) {
        Orders entity = ordersMapper.toOrders(req);
        Orders saved = ordersDao.createOrder(entity);
        return ordersMapper.toOrdersResponse(saved);
    }

    @Override
    public OrdersResponse updateOrder(long orderId, OrdersRequest req) {
        Orders entity = ordersMapper.toOrders(req);
        Orders updated = ordersDao.updateOrder(orderId, entity);

        if (updated == null) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }
        return ordersMapper.toOrdersResponse(updated);
    }

    @Override
    public List<OrdersResponse> getOrdersByUsername(String username,
                                                    int pageNumber,
                                                    int pageSize) {

        if (pageNumber < 1) pageNumber = 1;
        if (pageSize < 1) pageSize = 10;
        int offset = (pageNumber - 1) * pageSize;

        List<Orders> orders = ordersDao.findByUserUsername(username, pageSize, offset);

        for (Orders o : orders) {
            List<OrderItemsResponse> detail = ordersDao.getItemDetailsWithProductInfo(o.getOrderId());

            List<OrderItems> items = detail.stream()
                    .map(orderItemsMapper::toOrderItemsEntity) // ✅ dùng đúng mapper
                    .toList();

            o.setOrderItems(items);
        }

        return orders.stream()
                .map(ordersMapper::toOrdersResponse)
                .toList();
    }

}
