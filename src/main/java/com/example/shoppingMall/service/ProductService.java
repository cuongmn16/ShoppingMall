package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.ProductRequest;
import com.example.shoppingMall.dto.response.ProductDetailResponse;
import com.example.shoppingMall.dto.response.ProductResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductResponse> getAllProducts(String search, String category, String stock, int pageNumber, int pageSize);

    List<ProductResponse> getAllProductsByCategoryId(long categoryId, int pageNumber, int pageSize);

    ProductDetailResponse getProductDetail(long productId);
//
//    ProductDetailResponse getProductDetail(UUID productId);

    ProductResponse addProduct(HttpServletRequest request, ProductRequest productRequest);

    ProductResponse updateProduct( ProductRequest productRequest);

    List<ProductResponse> findProductsByKeyword(String keyword);

    List<ProductResponse> getRecommendedProducts(int limit);

}
