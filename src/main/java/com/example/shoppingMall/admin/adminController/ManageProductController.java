package com.example.shoppingMall.admin.adminController;

import com.example.shoppingMall.admin.adminService.ManageProductService;
import com.example.shoppingMall.dto.response.ApiResponse;
import com.example.shoppingMall.dto.response.ProductResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/manage-product")
public class ManageProductController {
    @Autowired
    private ManageProductService manageProductService;
    @GetMapping
    public ApiResponse<Map<String, Object>> getAllProductBySellerId(
            HttpServletRequest request,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String stock
//            @RequestParam(defaultValue = "1") int pageNumber,
//            @RequestParam(defaultValue = "10") int pageSize
    ) {
        List<ProductResponse> products = manageProductService.getAllProductsBySellerId(request,search, category, stock);
        // Tính toán stats (có thể di chuyển vào service)
        long totalProducts = manageProductService.getAllProductsBySellerId(request,search, category, stock).size();
        double totalValue = products.stream()
                .mapToDouble(p -> p.getPrice() * p.getStockQuantity())
                .sum();
        long lowStockCount = products.stream()
                .filter(p -> p.getStockQuantity() < 10)
                .count();

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("products", products);
        responseData.put("stats", Map.of(
                "totalProducts", totalProducts,
                "totalValue", totalValue,
                "lowStockCount", lowStockCount
        ));

        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(responseData);
        return apiResponse;
    }
}
