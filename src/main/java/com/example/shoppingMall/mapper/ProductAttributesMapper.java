package com.example.shoppingMall.mapper;

import com.example.shoppingMall.dto.request.ProductAttributesRequest;
import com.example.shoppingMall.dto.response.ProductAttributesResponse;
import com.example.shoppingMall.model.ProductAttributes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductAttributesMapper {


    ProductAttributes toProductAttributesRequest(ProductAttributesRequest productAttributesRequest);

    ProductAttributesResponse toProductAttributesResponse(ProductAttributes productAttributes);
}
