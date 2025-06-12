package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.response.ProductAttributesResponse;

import java.util.List;

public interface ProductAttributesService {

    List<ProductAttributesResponse> getAllProductAttributes(long productId);


}
