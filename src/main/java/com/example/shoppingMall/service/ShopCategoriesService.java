package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.ShopCategoriesRequest;
import com.example.shoppingMall.dto.response.ShopCategoriesResponse;

import java.util.List;
import java.util.UUID;

public interface ShopCategoriesService {
    List<ShopCategoriesResponse> getAllShopCategories();

    ShopCategoriesResponse getShopCategoryById(UUID categoryId);

    ShopCategoriesResponse createShopCategory(ShopCategoriesRequest shopCategoriesRequest);

    ShopCategoriesResponse updateShopCategory(UUID categoryId, ShopCategoriesRequest shopCategoriesRequest);

    void deleteShopCategory(UUID categoryId);

//    List<ShopCategoriesResponse> getAllShopCategoriesByParentId(Long parentId);
//
//    List<ShopCategoriesResponse> getCategoriesByParentNull();
}
