package com.example.shoppingMall.mapper;

import com.example.shoppingMall.dto.request.SellerRequest;
import com.example.shoppingMall.dto.response.SellerResponse;
import com.example.shoppingMall.model.Seller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    @Mapping(target = "products", ignore = true)
    Seller toSellerRequest(SellerRequest sellerRequest);

    SellerResponse toSellerResponse(Seller seller);
}
