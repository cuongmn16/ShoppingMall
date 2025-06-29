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

        List<OrderItemsResponse> data = orderItemsService.getAllOrderItems(orderId);

        ApiResponse<List<OrderItemsResponse>> api = new ApiResponse<>();
        api.setResult(data);
        return api;
    }

    /** Lấy chi tiết 1 item */
    @GetMapping("/{itemId}")
    public ApiResponse<OrderItemsResponse> getItem(@PathVariable long itemId) {
        OrderItemsResponse data = orderItemsService.getOrderItem(itemId);

        ApiResponse<OrderItemsResponse> api = new ApiResponse<>();
        api.setResult(data);
        return api;
    }

    /** Thêm mới item vào đơn */
    @PostMapping
    public ApiResponse<OrderItemsResponse> addItem(@RequestBody OrderItemsRequest req) {
        OrderItemsResponse data = orderItemsService.addOrderItem(req);

        ApiResponse<OrderItemsResponse> api = new ApiResponse<>();
        api.setResult(data);
        return api;
    }

    /** Cập nhật item */
    @PutMapping("/{itemId}")
    public ApiResponse<OrderItemsResponse> updateItem(
            @PathVariable long itemId,
            @RequestBody OrderItemsRequest req) {

        OrderItemsResponse data = orderItemsService.updateOrderItem(itemId, req);

        ApiResponse<OrderItemsResponse> api = new ApiResponse<>();
        api.setResult(data);
        return api;
    }

    /** (Tuỳ chọn) Xoá item */
    @DeleteMapping("/{itemId}")
    public ApiResponse<Void> deleteItem(@PathVariable long itemId) {
        orderItemsService.deleteOrderItem(itemId);   // Nếu bạn có hàm delete
        ApiResponse<Void> api = new ApiResponse<>();
        api.setMessage("Deleted");
        return api;
    }
}
