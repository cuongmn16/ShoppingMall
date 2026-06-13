package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.PermissionRequest;
import com.example.shoppingMall.dto.response.PermissionResponse;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.exception.ErrorCode;
import com.example.shoppingMall.mapper.PermissionMapper;
import com.example.shoppingMall.entity.Permission;
import com.example.shoppingMall.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        Permission permission = permissionMapper.toPermissionRequest(permissionRequest);
        permission = permissionRepository.save(permission);
        return permissionMapper.ToPermissionResponse(permission);
    }

    public List<PermissionResponse> getAllPermission() {
        return  permissionRepository.findAll().stream().map(permissionMapper::ToPermissionResponse).toList();
    }

    public void deletePermission(String  permission){
        permissionRepository.deleteById(permission);
    }
}
