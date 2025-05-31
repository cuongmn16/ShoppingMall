package com.example.shoppingMall.service;

import com.example.shoppingMall.dao.UserDao;
import com.example.shoppingMall.dto.request.UserCreationRequest;
import com.example.shoppingMall.dto.response.UserResponse;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.exception.ErrorCode;
import com.example.shoppingMall.mapper.UserMapper;
import com.example.shoppingMall.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserMapper userMapper;


    @Override
    public List<UserResponse> getAllUsers() {
        return userDao.getAllUsers().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse createUser(UserCreationRequest userCreationRequest) {
        if(userDao.isUserExistsByUsername(userCreationRequest.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(userCreationRequest);
        user = userDao.createUser(user);
    return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getUserById(long userId) {
        User user = userDao.getUserById(userId);
        if(user == null) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
     return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteUser(long userId) {
        userDao.deleteUser(userId);
    }
}
