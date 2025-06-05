package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.Seller;

import java.util.List;

public interface SellerDao {
    List<Seller> getAllSellers();

    Seller getSellerById(long sellerId);

    Seller createSeller(Seller seller);

    Seller updateSeller(long sellerId, Seller seller);

    boolean isSellerExists(long sellerId);

    void deleteSeller(long sellerId);


}
