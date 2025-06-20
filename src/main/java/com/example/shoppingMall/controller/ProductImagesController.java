package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.request.ProductImagesRequest;
import com.example.shoppingMall.dto.response.ApiResponse;
import com.example.shoppingMall.dto.response.ProductImagesResponse;
import com.example.shoppingMall.dto.response.ProductResponse;
import com.example.shoppingMall.service.ProductImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/images")
public class ProductImagesController {

    @Autowired
    private ProductImagesService productImagesService;

    @GetMapping("/{productId}")
    public ApiResponse<List<ProductImagesResponse>> getAllProductImages(@PathVariable long productId) {
        ApiResponse<List<ProductImagesResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productImagesService.getAllProductImages(productId));
        return apiResponse;
    }

    @GetMapping("/image/{imageId}")
    public ApiResponse<ProductImagesResponse> getProductImageById(@PathVariable long productId, long imageId) {
        ApiResponse<ProductImagesResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productImagesService.getProductImageById(productId, imageId));
        return apiResponse;
    }

    @DeleteMapping("/product/{productId}")
    public ApiResponse<ProductImagesResponse> deleteProductImageById(@PathVariable long productId, long imageId) {
        ApiResponse<ProductImagesResponse> apiResponse = new ApiResponse<>();
        productImagesService.deleteProductImage(productId, imageId);
        apiResponse.setMessage("Product image deleted successfully");
        return apiResponse;
    }

    @PostMapping("/add/{productId}")
    public ApiResponse<ProductImagesResponse> addProductImage(@PathVariable long productId, ProductImagesRequest productImagesRequest) {
        ApiResponse<ProductImagesResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productImagesService.addProductImage(productId, productImagesRequest));
        return apiResponse;
    }

    @PostMapping("/update/{productId}")
    public ApiResponse<ProductImagesResponse> updateProductImage(@PathVariable long productId, long imageId, ProductImagesRequest productImagesRequest) {
        ApiResponse<ProductImagesResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productImagesService.updateProductImage(productId, imageId, productImagesRequest));
        return apiResponse;
    }
    
}
