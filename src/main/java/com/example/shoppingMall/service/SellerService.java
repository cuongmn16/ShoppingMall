package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.SellerRequest;
import com.example.shoppingMall.dto.response.SellerResponse;
import com.example.shoppingMall.model.Seller;

import java.util.List;

public interface SellerService {
    List<SellerResponse> getAllSellers();

    SellerResponse createSeller(SellerRequest sellerRequest);
}
