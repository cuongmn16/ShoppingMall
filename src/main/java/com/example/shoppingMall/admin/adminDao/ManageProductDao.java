package com.example.shoppingMall.admin.adminDao;

import com.example.shoppingMall.model.Product;

import java.util.List;

public interface ManageProductDao {
    List<Product> getAllProductsBySellerId(long userId,String search, String category, String stock);

}
