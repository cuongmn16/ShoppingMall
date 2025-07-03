package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.request.ProductVariationsRequest;
import com.example.shoppingMall.dto.response.ApiResponse;
import com.example.shoppingMall.dto.response.ProductVariationResponse;
import com.example.shoppingMall.service.ProductVariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/variations")
public class ProductVariationController {

    @Autowired
    private ProductVariationService productVariationService;

    /** Lấy tất cả variations theo productId */
    @GetMapping("/product/{productId}")
    public ApiResponse<List<ProductVariationResponse>> getVariationsByProductId(@PathVariable long productId) {
        List<ProductVariationResponse> variations = productVariationService.getVariationsByProductId(productId);

        ApiResponse<List<ProductVariationResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(variations);
        return apiResponse;
    }

    /** Thêm mới variation */
    @PostMapping
    public ApiResponse<ProductVariationResponse> addVariation(@RequestBody ProductVariationsRequest request) {
        ProductVariationResponse created = productVariationService.addVariation(request);

        ApiResponse<ProductVariationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(created);
        return apiResponse;
    }

    /** Cập nhật variation */
    @PutMapping("/{variationId}")
    public ApiResponse<ProductVariationResponse> updateVariation(
            @PathVariable long variationId,
            @RequestBody ProductVariationsRequest request) {
        ProductVariationResponse updated = productVariationService.updateVariation(variationId, request);

        ApiResponse<ProductVariationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(updated);
        return apiResponse;
    }

    /** Xoá variation */
    @DeleteMapping("/{variationId}")
    public ApiResponse<String> deleteVariation(@PathVariable long variationId) {
        productVariationService.deleteVariation(variationId);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setResult("Xoá variation thành công");
        return apiResponse;
    }

    /** Lấy optionInputs từ product */
    @GetMapping("/product/{productId}/options")
    public ApiResponse<Object> getOptionInputsByProductId(@PathVariable long productId) {
        var options = productVariationService.getOptionInputsOfProduct(productId);

        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setResult(options);
        return apiResponse;
    }

}
