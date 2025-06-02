package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.Permission;
import com.example.shoppingMall.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class RoleDaoImpl implements RoleDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public Role save(Role role){
        String sql = "INSERT INTO role(name, description) VALUES (?, ?)";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, role.getName());
            stmt.setString(2, role.getDescription());
            stmt.executeUpdate();
            return role;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Role saveRole(Role role) {
        String sql = "INSERT INTO role (name, description) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE description = VALUES(description)";
        String insertRolePermissionSql = "INSERT INTO role_permissions (role_name, permissions_name) VALUES (?, ?)";
        String checkPermissionSql = "SELECT COUNT(*) FROM permission WHERE name = ?";

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false); // Bật giao dịch

            try (PreparedStatement roleStmt = conn.prepareStatement(sql);
                 PreparedStatement checkStmt = conn.prepareStatement(checkPermissionSql);
                 PreparedStatement rolePermStmt = conn.prepareStatement(insertRolePermissionSql)) {

                // Lưu Role
                roleStmt.setString(1, role.getName());
                roleStmt.setString(2, role.getDescription());
                roleStmt.executeUpdate();

                // Kiểm tra và lưu quan hệ Role-Permission
                if (role.getPermissions() != null && !role.getPermissions().isEmpty()) {
                    for (Permission permission : role.getPermissions()) {
                        // Kiểm tra xem quyền có tồn tại không
                        checkStmt.setString(1, permission.getName());
                        ResultSet rs = checkStmt.executeQuery();
                        rs.next();
                        if (rs.getInt(1) == 0) {
                            throw new SQLException("Permission " + permission.getName() + " does not exist");
                        }
                        rs.close();

                        // Chèn vào role_permissions
                        rolePermStmt.setString(1, role.getName());
                        rolePermStmt.setString(2, permission.getName());
                        rolePermStmt.addBatch();
                    }
                    rolePermStmt.executeBatch();
                } else {
                    System.out.println("No permissions provided for role: " + role.getName());
                }

                conn.commit();
                return role;
            } catch (SQLException e) {
                System.err.println("SQL Error: " + e.getMessage());
                e.printStackTrace();
                conn.rollback();
                throw new RuntimeException("Error saving role: " + e.getMessage(), e);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error establishing connection: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Role> findAllRoles() {
        List<Role> roles = new ArrayList<>();
        Map<String , Role> roleMap = new HashMap<>();
        String sql = "SELECT r.*, p.name AS permissions_name, p.description AS permission_description " +
                     "FROM role r " +
                    "LEFT JOIN role_permissions rp ON r.name = rp.role_name " +
                     "LEFT JOIN permission p ON rp.permissions_name = p.name";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            while (rs.next()) {
                String roleName = rs.getString("name");
                Role role = roleMap.get(roleName);
                if(role == null){
                    role = new Role();
                    role.setName(rs.getString("name"));
                    role.setDescription(rs.getString("description"));
                    roleMap.put(roleName, role);
                    roles.add(role);
                }
                String permissionName = rs.getString("permissions_name");
                String permissionDescription = rs.getString("permission_description");
                if (permissionName != null && permissionDescription  != null){
                    Permission permission = new Permission();
                    permission.setName(permissionName);
                    permission.setDescription(permissionDescription);
                    role.getPermissions().add(permission);
                }

            }
            return roles;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching roles: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteRole(String name) {
        String sql = "DELETE FROM role WHERE name = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting role: " + e.getMessage(), e);
        }
    }

    @Override
    public Role getRoleByName(String name) {
        Role role = null;
        Map<String , Role> roleMap = new HashMap<>();
        String sql = "SELECT r.*, p.name AS permissions_name, p.description AS permission_description " +
                     "FROM role r " +
                     "LEFT JOIN role_permissions rp ON r.name = rp.role_name " +
                     "LEFT JOIN permission p ON rp.permissions_name = p.name " +
                     "WHERE r.name = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
                 stmt.setString(1, name);
                    ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String roleName = rs.getString("name");
                role = roleMap.get(roleName);
                if (role == null) {
                    role = new Role();
                    role.setName(rs.getString("name"));
                    role.setDescription(rs.getString("description"));
                    roleMap.put(roleName, role);
                }
                String permissionName = rs.getString("permissions_name");
                String permissionDescription = rs.getString("permission_description");
                if (permissionName != null && permissionDescription != null) {
                    Permission permission = new Permission();
                    permission.setName(permissionName);
                    permission.setDescription(permissionDescription);
                    role.getPermissions().add(permission);
                }
            }
            return role;

        } catch (SQLException e) {
            throw new RuntimeException(e);
            }
    }

    @Override
    public Set<Role> getAllRolesByNames(Set<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
            return new HashSet<>();
        }
        Set<Role> roles = new HashSet<>();
        StringBuilder stringBuilder = new StringBuilder("?");
        for (int i = 1; i < roleNames.size(); i++) {
            stringBuilder.append("?,");
        }
        String sql = "SELECT r.*, p.name AS permissions_name, p.description AS permission_description " +
                     "FROM role r " +
                     "LEFT JOIN role_permissions rp ON r.name = rp.role_name " +
                     "LEFT JOIN permission p ON rp.permissions_name = p.name " +
                     "WHERE r.name IN (" + stringBuilder + ")";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            for (String roleName : roleNames) {
                stmt.setString(index++, roleName);
            }
            ResultSet rs = stmt.executeQuery();
            Map<String, Role> roleMap = new HashMap<>();
            while (rs.next()) {
                String roleName = rs.getString("name");
                Role role = roleMap.get(roleName);
                if (role == null) {
                    role = new Role();
                    role.setName(roleName);
                    role.setDescription(rs.getString("description"));
                    roleMap.put(roleName, role);
                    roles.add(role);
                }
                String permissionName = rs.getString("permissions_name");
                String permissionDescription = rs.getString("permission_description");
                if (permissionName != null && permissionDescription != null) {
                    Permission permission = new Permission();
                    permission.setName(permissionName);
                    permission.setDescription(permissionDescription);
                    role.getPermissions().add(permission);
                }
            }
            return roles;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching roles by names: " + e.getMessage(), e);
        }

    }
}
