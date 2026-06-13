package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.ProductImagesRequest;
import com.example.shoppingMall.dto.response.ProductImagesResponse;

import java.util.List;
import java.util.UUID;

public interface ProductImagesService {
    List<ProductImagesResponse> getAllProductImages(UUID productId);

    ProductImagesResponse addProductImage(UUID productId, ProductImagesRequest productImagesRequest);

    ProductImagesResponse updateProductImage(UUID productId, UUID imageId, ProductImagesRequest productImagesRequest);

    void deleteProductImage(UUID productId, UUID imageId);

    ProductImagesResponse getProductImageById(UUID productId, UUID imageId);

}
