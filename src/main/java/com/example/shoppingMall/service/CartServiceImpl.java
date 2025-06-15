package com.example.shoppingMall.service;

import com.example.shoppingMall.dao.CartDao;
import com.example.shoppingMall.dao.UserDao;
import com.example.shoppingMall.dto.request.CartRequest;
import com.example.shoppingMall.dto.response.CartResponse;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.exception.ErrorCode;
import com.example.shoppingMall.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private UserDao userDao;


    @Override
    public List<CartResponse> getAllCarts(String username) {
    if(!userDao.isUserExistsByUsername(username)){
        throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }
        return cartDao.getAllCarts(username);

    }

    @Override
    public CartResponse addToCart(String username,CartRequest cartRequest) {
        if(!userDao.isUserExistsByUsername(username)){
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        var cart = cartMapper.toCartRequest(cartRequest);
        var savedCart = cartDao.addToCart(username,cart);
        return cartMapper.toCartResponse(savedCart);
    }

    @Override
    public void deleteCart(long cartId) {
        cartDao.deleteCart(cartId);
    }
}
