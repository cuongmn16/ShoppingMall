package com.example.shoppingMall.mapper;


import com.example.shoppingMall.dto.request.ProductImagesRequest;
import com.example.shoppingMall.dto.response.ProductImagesResponse;
import com.example.shoppingMall.model.ProductImages;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductImagesMapper {

    ProductImages toProductImages(ProductImagesRequest productImagesRequest);

    ProductImagesResponse toProductImagesResponse(ProductImages productImages);


}
