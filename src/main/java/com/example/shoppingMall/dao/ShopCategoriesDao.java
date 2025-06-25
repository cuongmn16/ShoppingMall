package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.ShopCategories;

import java.util.List;

public interface ShopCategoriesDao {
    List<ShopCategories> getAllShopCategories();

    ShopCategories getShopCategoryById(long categoryId);

    List<ShopCategories> getAllShopCategoriesByParentId(long parentId);

    ShopCategories createShopCategory(ShopCategories shopCategory);

    void updateShopCategory(long categoryId,ShopCategories shopCategory);

    void deleteShopCategory(long categoryId);

    boolean isShopCategoryExistsById(long categoryId);

    List<ShopCategories> findCategoriesByParentNull();
}
