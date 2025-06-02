package com.example.shoppingMall.service;

import com.example.shoppingMall.dao.PermissionDao;
import com.example.shoppingMall.dao.RoleDao;
import com.example.shoppingMall.dto.request.RoleRequest;
import com.example.shoppingMall.dto.response.RoleResponse;
import com.example.shoppingMall.mapper.RoleMapper;
import com.example.shoppingMall.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public RoleResponse createRole(RoleRequest roleRequest){
        var role = roleMapper.toRoleRequest(roleRequest);
        var permissions = permissionDao.findAllPermissionsByNames(roleRequest.getPermissions());

        role.setPermissions(new HashSet<>(permissions));
        role = roleDao.saveRole(role);

        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> findAllRoles(){
        return roleDao.findAllRoles().stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }
    @Override
    public void deleteRole(String role){
        roleDao.deleteRole(role);
    }

}
