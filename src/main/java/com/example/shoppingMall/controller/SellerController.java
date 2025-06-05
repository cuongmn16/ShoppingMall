package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.request.SellerRequest;
import com.example.shoppingMall.dto.response.ApiResponse;
import com.example.shoppingMall.dto.response.SellerResponse;
import com.example.shoppingMall.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @GetMapping
    public ApiResponse<List<SellerResponse>> getAllSellers() {
        ApiResponse<List<SellerResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(sellerService.getAllSellers());
        return apiResponse;
    }

    @PostMapping
    public ApiResponse<SellerResponse> createSeller(@RequestBody SellerRequest sellerRequest) {
        ApiResponse<SellerResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(sellerService.createSeller(sellerRequest));
        return apiResponse;
    }

}
