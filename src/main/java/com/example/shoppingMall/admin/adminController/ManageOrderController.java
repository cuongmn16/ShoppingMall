package com.example.shoppingMall.admin.adminController;

import com.example.shoppingMall.admin.adminService.ManageOrderService;
import com.example.shoppingMall.dto.response.ApiResponse;
import com.example.shoppingMall.model.Orders;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class ManageOrderController {
    @Autowired
    private ManageOrderService manageOrderService;

    @GetMapping
    public ApiResponse<List<Orders>> getOrderItems(HttpServletRequest request) {
        ApiResponse<List<Orders>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(manageOrderService.getOrderItems(request));
        return apiResponse;
    }


}
