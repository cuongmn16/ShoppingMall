package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.ProductVariations;

import java.util.List;

public interface ProductVariationsDao {
    List<ProductVariations> getAllProductVariations(long productId);

    ProductVariations addProductVariation(long productId, ProductVariations productVariations);

    ProductVariations updateProductVariation(long productId, long variationId, ProductVariations productVariations);

    void deleteProductVariation(long productId, long variationId);

}
