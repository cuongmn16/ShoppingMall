package com.example.shoppingMall.mapper;

import com.example.shoppingMall.dto.request.ShopCategoriesRequest;
import com.example.shoppingMall.dto.response.ShopCategoriesResponse;
import com.example.shoppingMall.entity.ShopCategories;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ShopCategoriesMapper {

     void toShopCategoriesUpdate(@MappingTarget ShopCategories shopCategories, ShopCategoriesRequest shopCategoriesRequest);

    ShopCategories toShopCategories(ShopCategoriesRequest shopCategoriesRequest);

    ShopCategoriesResponse toShopCategoriesResponse(ShopCategories shopCategories);

}
