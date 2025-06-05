package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class PermissionDaoImpl implements PermissionDao {
    @Autowired
    private DataSource dataSource;
    @Override
    public Permission save(Permission permission) {
        String sql = "INSERT INTO permission (name, description) VALUES (?, ?)";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, permission.getName());
            stmt.setString(2, permission.getDescription());
            stmt.executeUpdate();
            return permission;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Permission> getAllPermissions() {
        List<Permission> permissions = new ArrayList<>();
        String sql = "SELECT * FROM permission";
        try(Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Permission permission = new Permission();
                permission.setName(rs.getString("name"));
                permission.setDescription(rs.getString("description"));
                permissions.add(permission);
            }
            return permissions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Permission getPermissionByName(String name) {
        Permission permission = null;
        String sql = "SELECT * FROM permission WHERE name = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                permission = new Permission();
                permission.setName(rs.getString("name"));
                permission.setDescription(rs.getString("description"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return permission;
    }

    @Override
    public void deletePermission(String permission) {
        String sql = "DELETE FROM permission WHERE name = ?";
        try(Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, permission);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isPermissionExists(String permission) {
    String sql = "SELECT COUNT(*) FROM permission WHERE name = ?";
    try(Connection conn = dataSource.getConnection();
    PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, permission);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
        return false;
    }

    @Override
    public Set<Permission> findAllPermissionsByNames(Set<String> permissionNames) {
        if (permissionNames == null || permissionNames.isEmpty()) {
            return new HashSet<>();
        }

        Set<Permission> permissions = new HashSet<>();
        StringBuilder placeholders = new StringBuilder("?");
        for (int i = 1; i < permissionNames.size(); i++) {
            placeholders.append(",?");
        }
        String sql = "SELECT name, description FROM permission WHERE name IN (" + placeholders + ")";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int index = 1;
            for (String name : permissionNames) {
                stmt.setString(index++, name);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Permission permission = new Permission();
                    permission.setName(rs.getString("name"));
                    permission.setDescription(rs.getString("description"));
                    permissions.add(permission);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching permissions: " + e.getMessage(), e);
        }

        // Kiểm tra xem tất cả permissionNames có được tìm thấy không
        if (permissions.size() != permissionNames.size()) {
            throw new IllegalArgumentException("Some permissions not found: " + permissionNames);
        }
        return permissions;
    }
}
