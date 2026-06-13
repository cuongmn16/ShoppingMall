package com.example.shoppingMall.service;
import com.example.shoppingMall.dto.request.RoleRequest;
import com.example.shoppingMall.dto.response.RoleResponse;
import com.example.shoppingMall.mapper.RoleMapper;
import com.example.shoppingMall.repository.PermissionRepository;
import com.example.shoppingMall.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public RoleResponse createRole(RoleRequest roleRequest){
        var role = roleMapper.toRoleRequest(roleRequest);
        var permissions = permissionRepository.findAllById(roleRequest.getPermissions());

        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> findAllRoles(){
        return roleRepository.findAll().stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }
    @Override
    public void deleteRole(String role){
        roleRepository.deleteById(role);
    }

}
