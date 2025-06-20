package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.ProductRequest;
import com.example.shoppingMall.dto.response.ProductDetailResponse;
import com.example.shoppingMall.dto.response.ProductResponse;
import com.example.shoppingMall.model.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts(int pageNumber, int pageSize);

    List<ProductResponse> getAllProductsByCategoryId(long categoryId, int pageNumber, int pageSize);

    ProductDetailResponse getProductDetail(long productId);

    ProductResponse addProduct(ProductRequest productRequest);

}
