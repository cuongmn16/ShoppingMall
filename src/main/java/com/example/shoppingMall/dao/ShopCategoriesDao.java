package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.ShopCategories;

import java.util.List;

public interface ShopCategoriesDao {
    List<ShopCategories> getAllShopCategories();

    ShopCategories getShopCategoryById(long categoryId);

    ShopCategories addShopCategory(ShopCategories shopCategory);

    void updateShopCategory(long categoryId,ShopCategories shopCategory);

    void deleteShopCategory(long categoryId);

    boolean isShopCategoryExistsById(long categoryId);


}
