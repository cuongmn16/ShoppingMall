package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.request.OrderItemsRequest;
import com.example.shoppingMall.dto.response.ApiResponse;
import com.example.shoppingMall.dto.response.OrderItemsResponse;
import com.example.shoppingMall.service.OrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemsController {

    @Autowired
    private OrderItemsService orderItemsService;

    /** Lấy tất cả item của 1 đơn */
    @GetMapping("/order/{orderId}")
    public ApiResponse<List<OrderItemsResponse>> getItemsByOrder(
            @PathVariable long orderId) {

        List<OrderItemsResponse> data = orderItemsService.getItemsByOrderId(orderId);

        ApiResponse<List<OrderItemsResponse>> api = new ApiResponse<>();
        api.setResult(data);
        return api;
    }

    /** Lấy chi tiết 1 item */
    @GetMapping("/{itemId}")
    public ApiResponse<OrderItemsResponse> getItem(@PathVariable long itemId) {
        OrderItemsResponse data = orderItemsService.getItemById(itemId);

        ApiResponse<OrderItemsResponse> api = new ApiResponse<>();
        api.setResult(data);
        return api;
    }

    /** Thêm mới item vào đơn */
    @PostMapping
    public ApiResponse<OrderItemsResponse> addItem(@RequestBody OrderItemsRequest req) {
        OrderItemsResponse data = orderItemsService.createItem(req);

        ApiResponse<OrderItemsResponse> api = new ApiResponse<>();
        api.setResult(data);
        return api;
    }

    /** Cập nhật item */
    @PutMapping("/{itemId}")
    public ApiResponse<OrderItemsResponse> updateItem(
            @PathVariable long itemId,
            @RequestBody OrderItemsRequest req) {

        OrderItemsResponse data = orderItemsService.updateItem(itemId, req);

        ApiResponse<OrderItemsResponse> api = new ApiResponse<>();
        api.setResult(data);
        return api;
    }

    @DeleteMapping("/{itemId}")
    public ApiResponse<Void> deleteItem(@RequestParam long itemId) {
        orderItemsService.deleteItem(itemId);

        ApiResponse<Void> api = new ApiResponse<>();
        api.setResult(null);
        return api;
    }

}
