package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.request.ShopCategoriesRequest;
import com.example.shoppingMall.dto.response.ApiResponse;
import com.example.shoppingMall.dto.response.ShopCategoriesResponse;
import com.example.shoppingMall.service.ShopCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class ShopCategoriesController {
    @Autowired
    private ShopCategoriesService shopCategoriesService;

    @GetMapping
    public ApiResponse<List<ShopCategoriesResponse>> getAllShopCategories() {
        ApiResponse<List<ShopCategoriesResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(shopCategoriesService.getAllShopCategories());
        return apiResponse;
    }

    @GetMapping("/parent-null")
    public ApiResponse<List<ShopCategoriesResponse>> getRootCategories() {
        ApiResponse<List<ShopCategoriesResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(shopCategoriesService.getCategoriesByParentNull());
        return apiResponse;
    }


    @GetMapping("/parent/{parentId}")
    public ApiResponse<List<ShopCategoriesResponse>> getCategoriesByParentId(@PathVariable long parentId) {
        ApiResponse<List<ShopCategoriesResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(shopCategoriesService.getAllShopCategoriesByParentId(parentId));
        return apiResponse;
    }

    @PostMapping
    public ApiResponse<ShopCategoriesResponse> createShopCategory(@RequestBody ShopCategoriesRequest shopCategoriesRequest) {
        ApiResponse<ShopCategoriesResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(shopCategoriesService.createShopCategory(shopCategoriesRequest));
        return apiResponse;
    }


}
