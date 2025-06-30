package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.request.ProductRequest;
import com.example.shoppingMall.dto.response.ApiResponse;
import com.example.shoppingMall.dto.response.ProductDetailResponse;
import com.example.shoppingMall.dto.response.ProductResponse;
import com.example.shoppingMall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ApiResponse<Map<String, Object>> getAllProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String stock,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<ProductResponse> products = productService.getAllProducts(search, category, stock, pageNumber, pageSize);
        // Tính toán stats (có thể di chuyển vào service)
        long totalProducts = productService.getAllProducts(search, category, stock, 1, Integer.MAX_VALUE).size();
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

    @GetMapping("/category/{categoryId}")
    public ApiResponse<List<ProductResponse>> getAllProductsByCategoryId(@PathVariable long categoryId,
                                                                         @RequestParam(defaultValue = "1") int pageNumber,
                                                                         @RequestParam(defaultValue = "10") int pageSize) {
        List<ProductResponse> products = productService.getAllProductsByCategoryId(categoryId, pageNumber, pageSize);
        ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(products);
        return apiResponse;
    }

    @GetMapping("/{productId}")
    public ApiResponse<ProductDetailResponse> getProductDetail(@PathVariable long productId) {
      ProductDetailResponse productDetailResponse = productService.getProductDetail(productId);
        ApiResponse<ProductDetailResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productDetailResponse);
        return apiResponse;
    }

    @PostMapping
    public ApiResponse<ProductResponse> addProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.addProduct(productRequest);
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productResponse);
        return apiResponse;
    }

    @GetMapping("/recommended")
    public ApiResponse<List<ProductResponse>> getRecommendedProducts(
            @RequestParam(defaultValue = "6") int limit) {

        List<ProductResponse> data = productService.getRecommendedProducts(limit);

        ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setMessage("success");
        apiResponse.setResult(data);

        return apiResponse;
    }

}
