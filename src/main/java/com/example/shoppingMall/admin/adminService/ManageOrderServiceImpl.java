package com.example.shoppingMall.admin.adminService;

import com.example.shoppingMall.admin.adminDao.ManageOrderDao;
import com.example.shoppingMall.dao.UserDao;
import com.example.shoppingMall.model.OrderItems;
import com.example.shoppingMall.model.Orders;
import com.example.shoppingMall.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageOrderServiceImpl implements  ManageOrderService {
    @Autowired
    private ManageOrderDao manageOrderDao;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserDao userDao;


    @Override
    public List<Orders> getOrderItems(HttpServletRequest request) {
            String username = authenticationService.extractUsernameFromRequest(request);
            long userId = userDao.getUserIdByUsername(username);

            return manageOrderDao.getAllOder(userId);

    }
}
