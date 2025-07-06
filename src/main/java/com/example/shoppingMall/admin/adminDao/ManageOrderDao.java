package com.example.shoppingMall.admin.adminDao;

import com.example.shoppingMall.model.Orders;

import java.util.List;

public interface ManageOrderDao {
    List<Orders> getAllOder(long userId);


}
