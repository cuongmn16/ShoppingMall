package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.ProductVariationsRequest;
import com.example.shoppingMall.dto.response.ProductVariationResponse;

import java.util.List;
import java.util.Map;

/**
 * Service layer for product variations (size / color combinations).
 */
public interface ProductVariationService {

    /** Lấy tất cả biến thể của một sản phẩm */
    List<ProductVariationResponse> getVariationsByProductId(long productId);

    /** Trả về map<optionType,List<optionValue>> của sản phẩm (phục vụ filter size/color ở FE) */
    Map<String, List<String>> getOptionInputsOfProduct(long productId);

    /** Thêm mới một biến thể */
    ProductVariationResponse addVariation(ProductVariationsRequest request);

    /** Cập nhật biến thể */
    ProductVariationResponse updateVariation(long variationId, ProductVariationsRequest request);

    /** Xoá biến thể */
    void deleteVariation(long variationId);
}