package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionDao {
    Permission save(Permission permission);

    List<Permission> getAllPermissions();

    Permission getPermissionByName(String name);

    void deletePermission(String permission);

    boolean isPermissionExists(String permission);

    Set<Permission> findAllPermissionsByNames(Set<String> permissionNames);



}
