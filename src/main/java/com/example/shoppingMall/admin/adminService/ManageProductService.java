package com.example.shoppingMall.admin.adminService;

import com.example.shoppingMall.dto.response.ProductResponse;
import com.example.shoppingMall.model.Product;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ManageProductService {
    List<ProductResponse> getAllProductsBySellerId(HttpServletRequest request, String search, String category, String stock, int pageNumber, int pageSize);
}
