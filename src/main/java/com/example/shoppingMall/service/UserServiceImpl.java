package com.example.shoppingMall.service;

import com.example.shoppingMall.constant.PredefinedRole;
import com.example.shoppingMall.dao.RoleDao;
import com.example.shoppingMall.dao.UserDao;
import com.example.shoppingMall.dto.request.UserUpdateRequest;
import com.example.shoppingMall.dto.request.UserCreationRequest;
import com.example.shoppingMall.dto.response.UserResponse;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.exception.ErrorCode;
import com.example.shoppingMall.mapper.UserMapper;
import com.example.shoppingMall.model.Role;
import com.example.shoppingMall.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleDao roleDao;


    @Override
    @PreAuthorize("hasRole('USER')")
    public List<UserResponse> getAllUsers() {
        return userDao.getAllUsers().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse createUser(UserCreationRequest userCreationRequest) {
        if(userDao.isUserExistsByUsername(userCreationRequest.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        if(userDao.isUserExistsByEmail(userCreationRequest.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        User user = userMapper.toUser(userCreationRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roles.add(roleDao.getRoleByName(PredefinedRole.USER_ROLE));
        user.setRoles(roles);

        user = userDao.createUser(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(long userId, UserUpdateRequest request) {
        User user = userDao.getUserById(userId);
        if(user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        userMapper.toUpdateUser(user, request);
        var roles = roleDao.getAllRolesByNames(request.getRoles());
        user.setRoles(roles);
        userDao.updateUser(user);

        return userMapper.toUserResponse(user);
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userDao.getUserByUsername(username);
        if(user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        return userMapper.toUserResponse(user);
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(long userId) {
        User user = userDao.getUserById(userId);
        if(user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
     return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteUser(long userId) {
        userDao.deleteUser(userId);
    }
}
