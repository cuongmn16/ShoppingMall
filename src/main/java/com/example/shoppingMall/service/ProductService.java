package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.ProductRequest;
import com.example.shoppingMall.dto.response.ProductResponse;
import com.example.shoppingMall.model.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts(int pageNumber, int pageSize);

    ProductResponse createProduct(ProductRequest productRequest);

}
