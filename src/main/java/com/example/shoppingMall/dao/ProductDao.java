package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts(int pageNumber, int pageSize);

    List<Product> getAllProductsByCategory(long categoryId);

    Product getProductById(long productId);

    Product addProduct(Product product);

    Product updateProduct(long productId, Product product);

    void deleteProduct(long productId);
}
