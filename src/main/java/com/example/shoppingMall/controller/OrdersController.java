package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.request.OrdersRequest;
import com.example.shoppingMall.dto.response.ApiResponse;
import com.example.shoppingMall.dto.response.OrderItemsResponse;
import com.example.shoppingMall.dto.response.OrdersResponse;
import com.example.shoppingMall.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    /** Lấy tất cả đơn hàng (phân trang) */
    @GetMapping
    public ApiResponse<List<OrdersResponse>> getAllOrders(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {

        List<OrdersResponse> data = ordersService.getAllOrders(pageNumber, pageSize);

        ApiResponse<List<OrdersResponse>> api = new ApiResponse<>();
        api.setResult(data);
        return api;
    }

    /** Lấy đơn hàng của một user (phân trang) */
    @GetMapping("/user/{userId}")
    public ApiResponse<List<OrdersResponse>> getOrdersByUserId(
            @PathVariable long userId,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        ApiResponse<List<OrdersResponse>> api = new ApiResponse<>();
        try {
            List<OrdersResponse> data = ordersService.getAllOrdersByUserId(userId, pageNumber, pageSize);
            api.setResult(data);
        } catch (Exception e) {
            e.printStackTrace(); // In log
            api.setCode(500);
            api.setMessage("Lỗi server: " + e.getMessage());
        }
        return api;
    }


    /** Lấy chi tiết 1 đơn hàng */
    @GetMapping("/{orderId}")
    public ApiResponse<OrdersResponse> getOrderDetail(@PathVariable long orderId) {
        OrdersResponse data = ordersService.getOrderDetail(orderId);

        ApiResponse<OrdersResponse> api = new ApiResponse<>();
        api.setResult(data);
        return api;
    }


    /** Tạo mới đơn hàng */
    @PostMapping
    public ApiResponse<OrdersResponse> addOrder(@RequestBody OrdersRequest ordersRequest) {
        OrdersResponse data = ordersService.addOrder(ordersRequest);

        ApiResponse<OrdersResponse> api = new ApiResponse<>();
        api.setResult(data);
        return api;
    }

    /** Cập nhật đơn hàng */
    @PutMapping("/{orderId}")
    public ApiResponse<OrdersResponse> updateOrder(
            @PathVariable long orderId,
            @RequestBody OrdersRequest ordersRequest) {

        OrdersResponse data = ordersService.updateOrder(orderId, ordersRequest);

        ApiResponse<OrdersResponse> api = new ApiResponse<>();
        api.setResult(data);
        return api;
    }

    @GetMapping("/cart/{userId}")
    public ApiResponse<OrdersResponse> getCartByUserId(@PathVariable long userId) {
        OrdersResponse cart = ordersService.getCartOrderByUserId(userId);
        ApiResponse<OrdersResponse> api = new ApiResponse<>();
        api.setResult(cart);
        return api;
    }

}
