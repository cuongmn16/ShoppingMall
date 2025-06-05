package com.example.shoppingMall.service;

import com.example.shoppingMall.dao.PermissionDao;
import com.example.shoppingMall.dto.request.PermissionRequest;
import com.example.shoppingMall.dto.response.PermissionResponse;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.exception.ErrorCode;
import com.example.shoppingMall.mapper.PermissionMapper;
import com.example.shoppingMall.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        Permission permission = permissionMapper.toPermissionRequest(permissionRequest);
        permission = permissionDao.save(permission);
        return permissionMapper.ToPermissionResponse(permission);
    }

    public List<PermissionResponse> getAllPermission() {
        var permissions = permissionDao.getAllPermissions();
        return permissions.stream().map(permissionMapper::ToPermissionResponse).toList();
    }

    public void deletePermission(String  permission){
        var exist = permissionDao.isPermissionExists(permission);
        if (!exist) {
            throw new AppException(ErrorCode.PERMISSION_NOT_FOUND);
        }
        permissionDao.deletePermission(permission);
    }
}
