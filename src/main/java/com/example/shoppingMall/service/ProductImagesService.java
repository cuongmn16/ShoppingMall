package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.ProductImagesRequest;
import com.example.shoppingMall.dto.response.ProductImagesResponse;

import java.util.List;

public interface ProductImagesService {
    List<ProductImagesResponse> getAllProductImages(long productId);

    ProductImagesResponse addProductImage(long productId, ProductImagesRequest productImagesRequest);

    ProductImagesResponse updateProductImage(long productId, long imageId, ProductImagesRequest productImagesRequest);

    void deleteProductImage(long productId, long imageId);

    ProductImagesResponse getProductImageById(long productId, long imageId);

}
