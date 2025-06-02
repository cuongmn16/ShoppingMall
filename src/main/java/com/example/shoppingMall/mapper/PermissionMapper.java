package com.example.shoppingMall.mapper;

import com.example.shoppingMall.dto.request.PermissionRequest;
import com.example.shoppingMall.dto.response.PermissionResponse;
import com.example.shoppingMall.model.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermissionRequest(PermissionRequest permissionRequest);

    PermissionResponse ToPermissionResponse(Permission permission);
}
