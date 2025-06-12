package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.response.ProductVariationsResponse;

import java.util.List;

public interface ProductVariationsService {

    List<ProductVariationsResponse> getAllProductVariations(long productId);
}
