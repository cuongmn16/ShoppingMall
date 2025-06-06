package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.response.ApiResponse;
import com.example.shoppingMall.dto.response.ProductImagesResponse;
import com.example.shoppingMall.dto.response.ProductResponse;
import com.example.shoppingMall.service.ProductImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products/images")
public class ProductImagesController {

    @Autowired
    private ProductImagesService productImagesService;

    @GetMapping("/{productId}")
    public ApiResponse<List<ProductImagesResponse>> getAllProductImages(@PathVariable long productId) {
        ApiResponse<List<ProductImagesResponse>> apiResponse = new ApiResponse<>();
        List<ProductImagesResponse> productImagesList = productImagesService.getAllProductImages(productId);
        apiResponse.setResult(productImagesList);
        return apiResponse;
    }
}
