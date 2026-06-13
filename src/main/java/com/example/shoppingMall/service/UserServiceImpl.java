package com.example.shoppingMall.service;

import com.example.shoppingMall.constant.PredefinedRole;
import com.example.shoppingMall.dto.request.UserUpdateRequest;
import com.example.shoppingMall.dto.request.UserCreationRequest;
import com.example.shoppingMall.dto.response.UserResponse;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.exception.ErrorCode;
import com.example.shoppingMall.mapper.UserMapper;
import com.example.shoppingMall.entity.Role;
import com.example.shoppingMall.entity.User;
import com.example.shoppingMall.repository.RoleRepository;
import com.example.shoppingMall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse createUser(UserCreationRequest userCreationRequest) {
        if(userRepository.existsByUsername(userCreationRequest.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        if(userRepository.existsByEmail(userCreationRequest.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        User user = userMapper.toUser(userCreationRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepository.getRoleByName(PredefinedRole.USER_ROLE));
        user.setRoles(roles);

        user = userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(UUID userId, UserUpdateRequest request) {
        var user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.toUpdateUser(user, request);
//        var roles = roleRepository.findAll(request.getRoles());
//        user.setRoles(roles);
        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }
    @Override
    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        var user = userRepository.findByUsername(username)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
     return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }
}
