package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.User;

import java.util.List;

public interface UserDao {
    public List<User> getAllUsers();

    public User getUserById(long userId);

    public User getUserByUsername(String username);

    public User createUser(User user);

    public void updateUser(User user);

    public void deleteUser(long userId);

    public boolean isUserExistsById(String username);

    public boolean isUserExistsByUsername(String username);


}