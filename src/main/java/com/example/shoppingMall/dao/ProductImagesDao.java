package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.ProductImages;

import java.util.List;

public interface ProductImagesDao {

    List<ProductImages> getAllProductImages(long productId);

    void addProductImage(long productId,ProductImages productImages);

    ProductImages updateProductImage(long productId, long imageId, ProductImages productImages);

    void deleteProductImage(long productId, long imageId);

    ProductImages getProductImageById(long productId, long imageId);

    boolean isProductImageExistsById(long productId, long imageId);
}
