package com.example.shoppingMall.admin.adminService;

import com.example.shoppingMall.model.OrderItems;
import com.example.shoppingMall.model.Orders;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ManageOrderService {
    List<Orders>  getOrderItems(HttpServletRequest request);
}
