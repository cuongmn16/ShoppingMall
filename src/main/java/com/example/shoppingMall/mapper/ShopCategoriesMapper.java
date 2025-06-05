package com.example.shoppingMall.mapper;

import com.example.shoppingMall.dto.request.ShopCategoriesRequest;
import com.example.shoppingMall.dto.response.ShopCategoriesResponse;
import com.example.shoppingMall.model.ShopCategories;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopCategoriesMapper {

    ShopCategories toShopCategories(ShopCategoriesRequest shopCategoriesRequest);

    ShopCategoriesResponse toShopCategoriesResponse(ShopCategories shopCategories);

}
