package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.ShopCategories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShopCategoriesRepository extends JpaRepository<ShopCategories, UUID> {
}
