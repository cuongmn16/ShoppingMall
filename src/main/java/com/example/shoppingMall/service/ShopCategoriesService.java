package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.ShopCategoriesRequest;
import com.example.shoppingMall.dto.response.ShopCategoriesResponse;

import java.util.List;

public interface ShopCategoriesService {
    List<ShopCategoriesResponse> getAllShopCategories();

    ShopCategoriesResponse getShopCategoryById(long categoryId);

    ShopCategoriesResponse addShopCategory(ShopCategoriesRequest shopCategoriesRequest);

    void updateShopCategory(long categoryId, ShopCategoriesRequest shopCategoriesRequest);

    void deleteShopCategory(long categoryId);

}
