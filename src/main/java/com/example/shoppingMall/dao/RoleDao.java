package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {

    Role save(Role role);

    Role saveRole(Role role);

    List<Role> findAllRoles();

    void deleteRole(String name);

    Role getRoleByName(String name);

    Set<Role> getAllRolesByNames(Set<String> roleNames);


}
