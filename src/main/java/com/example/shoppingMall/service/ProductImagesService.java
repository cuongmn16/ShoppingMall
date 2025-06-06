package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.response.ProductImagesResponse;

import java.util.List;

public interface ProductImagesService {
    List<ProductImagesResponse> getAllProductImages(long productId);


}
