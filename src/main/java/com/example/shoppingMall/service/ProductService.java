package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.ProductRequest;
import com.example.shoppingMall.dto.response.ProductDetailResponse;
import com.example.shoppingMall.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts(int pageNumber, int pageSize);

    ProductDetailResponse getProductDetail(long productId);

    ProductResponse createProduct(ProductRequest productRequest);

}
