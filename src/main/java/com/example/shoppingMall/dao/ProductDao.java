package com.example.shoppingMall.dao;

import com.example.shoppingMall.dto.response.ProductDetailResponse;
import com.example.shoppingMall.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts(String search, String category, String stock, int pageNumber, int pageSize);

    List<Product> getAllProductsByCategory(long categoryId,int pageNumber, int pageSize);

    ProductDetailResponse getProductById(long productId);

    Product addProduct(Product product);

    Product updateProduct(long productId, Product product);

    void deleteProduct(long productId);

    List<Product> findProductsByKeyword(String keyword);

    List<Product> getRecommendedProducts(int limit);
}
