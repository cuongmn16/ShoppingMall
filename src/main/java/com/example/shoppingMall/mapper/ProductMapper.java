package com.example.shoppingMall.mapper;

import com.example.shoppingMall.dto.request.ProductRequest;
import com.example.shoppingMall.dto.response.ProductDetailResponse;
import com.example.shoppingMall.dto.response.ProductResponse;
import com.example.shoppingMall.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "productImages", ignore = true)
    Product toProductRequest(ProductRequest productRequest);

    ProductResponse toProduct(Product product);




}
