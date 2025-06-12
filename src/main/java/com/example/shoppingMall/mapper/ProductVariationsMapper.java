package com.example.shoppingMall.mapper;

import com.example.shoppingMall.dto.request.ProductAttributesRequest;
import com.example.shoppingMall.dto.request.ProductVariationsRequest;
import com.example.shoppingMall.dto.response.ProductAttributesResponse;
import com.example.shoppingMall.dto.response.ProductVariationsResponse;
import com.example.shoppingMall.model.ProductAttributes;
import com.example.shoppingMall.model.ProductVariations;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductVariationsMapper {
    ProductVariations toProductVariationsRequest(ProductVariationsRequest productVariationsRequest);

    ProductVariationsResponse toProductVariationsResponse(ProductVariations productVariations);

}
