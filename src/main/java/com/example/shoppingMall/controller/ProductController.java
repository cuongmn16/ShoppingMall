package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.request.ProductRequest;
import com.example.shoppingMall.dto.response.ApiResponse;
import com.example.shoppingMall.dto.response.ProductDetailResponse;
import com.example.shoppingMall.dto.response.ProductResponse;
import com.example.shoppingMall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAllProducts(@RequestParam(defaultValue = "1") int pageNumber,
                                                             @RequestParam(defaultValue = "10") int pageSize) {
        List<ProductResponse> products = productService.getAllProducts(pageNumber, pageSize);
        ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(products);
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
    public ApiResponse<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.addProduct(productRequest);
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productResponse);
        return apiResponse;
    }
}
