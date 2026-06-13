package com.example.shoppingMall.mapper;

import com.example.shoppingMall.dto.request.ProductRequest;
import com.example.shoppingMall.dto.response.ProductDetailResponse;
import com.example.shoppingMall.dto.response.ProductResponse;
import com.example.shoppingMall.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProductRequest(ProductRequest productRequest);

    ProductResponse toProduct(Product product);

    ProductDetailResponse toProductDetail(Product product);




}
