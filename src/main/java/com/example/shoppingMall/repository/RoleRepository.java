package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {
    Role getRoleByName(String Role);
}
