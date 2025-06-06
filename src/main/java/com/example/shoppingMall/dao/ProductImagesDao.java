package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.ProductImages;

import java.util.List;

public interface ProductImagesDao {

    List<ProductImages> getAllProductImages(long productId);

    ProductImages addProductImage(long productId,ProductImages productImages);

    ProductImages updateProductImage(long productId, long imageId, ProductImages productImages);

    void deleteProductImage(long productId, long imageId);


}
