package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.RoleRequest;
import com.example.shoppingMall.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse createRole(RoleRequest roleRequest);
    List<RoleResponse> findAllRoles();
    void deleteRole(String role);
}
