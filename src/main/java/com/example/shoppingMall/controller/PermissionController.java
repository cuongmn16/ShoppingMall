package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.request.PermissionRequest;
import com.example.shoppingMall.dto.response.ApiResponse;
import com.example.shoppingMall.dto.response.PermissionResponse;
import com.example.shoppingMall.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping
    public ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest permissionRequest) {
        ApiResponse<PermissionResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(permissionService.createPermission(permissionRequest));
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAllPermissions() {
        ApiResponse<List<PermissionResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(permissionService.getAllPermission());
        return apiResponse;
    }

    @DeleteMapping("/{permission}")
    public ApiResponse<Void> deletePermission(@PathVariable String permission) {
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        permissionService.deletePermission(permission);
        apiResponse.setMessage("Permission deleted successfully");
        return apiResponse;
    }



}
