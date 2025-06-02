package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.request.PermissionRequest;
import com.example.shoppingMall.dto.request.RoleRequest;
import com.example.shoppingMall.dto.response.ApiResponse;
import com.example.shoppingMall.dto.response.PermissionResponse;
import com.example.shoppingMall.dto.response.RoleResponse;
import com.example.shoppingMall.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping
    public ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        ApiResponse<RoleResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(roleService.createRole(roleRequest));
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAllRoles() {
        ApiResponse<List<RoleResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(roleService.findAllRoles());
        return apiResponse;
    }

    @DeleteMapping("/{role}")
    public ApiResponse<Void> deleteRole(@PathVariable String role) {
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        roleService.deleteRole(role);
        apiResponse.setMessage("Role deleted successfully");
        return apiResponse;
    }
    
}
