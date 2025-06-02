package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    User getUserById(long userId);

    User getUserByUsername(String username);

    User createUser(User user);

    void updateUser(User user);

    void deleteUser(long userId);

    boolean isUserExistsById(String username);

    boolean isUserExistsByUsername(String username);

    boolean isUserExistsByEmail(String email);

}