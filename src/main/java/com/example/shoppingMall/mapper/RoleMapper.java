package com.example.shoppingMall.mapper;

import com.example.shoppingMall.dto.request.RoleRequest;
import com.example.shoppingMall.dto.response.RoleResponse;
import com.example.shoppingMall.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRoleRequest(RoleRequest roleRequest);

    RoleResponse toRoleResponse(Role role);
}
