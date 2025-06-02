package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.PermissionRequest;
import com.example.shoppingMall.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse createPermission(PermissionRequest permissionRequest);

    List<PermissionResponse> getAllPermission();

    void deletePermission(String  permission);
}
