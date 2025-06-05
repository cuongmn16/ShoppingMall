package com.example.shoppingMall.dao;

import com.example.shoppingMall.enums.AccountStatus;
import com.example.shoppingMall.enums.Gender;
import com.example.shoppingMall.model.Permission;
import com.example.shoppingMall.model.Role;
import com.example.shoppingMall.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;


@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private DataSource dataSource;



    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Map<Long, User> userMap = new HashMap<>();
        Map<String, Role> roleMap = new HashMap<>();

        String sql = "SELECT u.*, " +
                "r.name AS roles_name, r.description AS role_description, " +
                "p.name AS permissions_name, p.description AS permission_description " +
                "FROM users u " +
                "LEFT JOIN user_roles ur ON u.user_id = ur.user_id " +
                "LEFT JOIN role r ON ur.roles_name = r.name " +
                "LEFT JOIN role_permissions rp ON r.name = rp.role_name " +
                "LEFT JOIN permission p ON rp.permissions_name = p.name";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Long userId = rs.getLong("user_id");

                // Nếu user chưa tồn tại trong map, tạo mới
                User user = userMap.get(userId);
                if (user == null) {
                    user = new User();
                    user.setUserId(userId);
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    user.setFullName(rs.getString("full_name"));
                    user.setPhone(rs.getString("phone"));
                    user.setAvatarUrl(rs.getString("avatar_url"));

                    Date date = rs.getDate("date_of_birth");
                    if (date != null) {
                        user.setDateOfBirth(date.toLocalDate());
                    } else {
                        user.setDateOfBirth(null);
                    }

                    String gender = rs.getString("gender");
                    if (gender != null) {
                        user.setGender(Gender.valueOf(gender));
                    } else {
                        user.setGender(null);
                    }

                    String accountStatus = rs.getString("account_status");
                    if (accountStatus != null) {
                        user.setAccountStatus(AccountStatus.valueOf(accountStatus));
                    } else {
                        user.setAccountStatus(null);
                    }

                    userMap.put(userId, user);
                    users.add(user);
                }

                // Thêm vai trò vào Set<RoleResponse> nếu có
                String roleName = rs.getString("roles_name");
                String roleDescription = rs.getString("role_description");
                if (roleName != null && roleDescription != null) {
                   Role role = roleMap.get(roleName);
                   if(role == null){
                       Set<Permission> permissions = new HashSet<>();
                      role = new Role(roleName, roleDescription, permissions);
                          roleMap.put(roleName, role);
                          user.getRoles().add(role);
                   }

                   String permissionName = rs.getString("permissions_name");
                   String permissionDescription = rs.getString("permission_description");
                   if(permissionName != null && permissionDescription != null){
                       Permission permission = new Permission();
                       permission.setName(permissionName);
                       permission.setDescription(permissionDescription);
                       role.getPermissions().add(permission);
                   }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User getUserById(long userId) {
        User user = null;
        Map<Long, User> userMap = new HashMap<>();
        Map<String, Role> roleMap = new HashMap<>();
        String sql ="SELECT u.*, " +
                "r.name AS roles_name, r.description AS role_description, " +
                "p.name AS permissions_name, p.description AS permission_description " +
                "FROM users u " +
                "LEFT JOIN user_roles ur ON u.user_id = ur.user_id " +
                "LEFT JOIN role r ON ur.roles_name = r.name " +
                "LEFT JOIN role_permissions rp ON r.name = rp.role_name " +
                "LEFT JOIN permission p ON rp.permissions_name = p.name " +
                "WHERE u.user_id = ?";
        try(Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Long currentUserId = rs.getLong("user_id");
                user = userMap.get(currentUserId);
                if (user == null) {
                    user = new User();
                    user.setUserId(rs.getLong("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    user.setFullName(rs.getString("full_name"));
                    user.setPhone(rs.getString("phone"));
                    user.setAvatarUrl(rs.getString("avatar_url"));

                    Date date = rs.getDate("date_of_birth");
                    if (date != null) {
                        user.setDateOfBirth(date.toLocalDate());
                    } else {
                        user.setDateOfBirth(null);
                    }

                    String gender = rs.getString("gender");
                    if(gender != null){
                        user.setGender(Gender.valueOf(gender));
                    }else{
                        user.setGender(null);
                    }

                    String accountStatus = rs.getString("account_status");
                    if(accountStatus != null){
                        user.setAccountStatus(AccountStatus.valueOf(accountStatus));
                    }else{
                        user.setAccountStatus(null);
                    }
                    userMap.put(userId, user);
                }
                String roleName = rs.getString("roles_name");
                String roleDescription = rs.getString("role_description");
                if (roleName != null && roleDescription != null) {
                    Role role = roleMap.get(roleName);
                    if(role == null){
                        Set<Permission> permissions = new HashSet<>();
                        role = new Role(roleName, roleDescription, permissions);
                        roleMap.put(roleName, role);
                        user.getRoles().add(role);
                    }

                    String permissionName = rs.getString("permissions_name");
                    String permissionDescription = rs.getString("permission_description");
                    if(permissionName != null && permissionDescription != null){
                        Permission permission = new Permission();
                        permission.setName(permissionName);
                        permission.setDescription(permissionDescription);
                        role.getPermissions().add(permission);
                    }
                }

            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        User user = null;
        Map<Long, User> userMap = new HashMap<>();
        Map<String, Role> roleMap = new HashMap<>();
        String sql ="SELECT u.*, " +
                "r.name AS roles_name, r.description AS role_description, " +
                "p.name AS permissions_name, p.description AS permission_description " +
                "FROM users u " +
                "LEFT JOIN user_roles ur ON u.user_id = ur.user_id " +
                "LEFT JOIN role r ON ur.roles_name = r.name " +
                "LEFT JOIN role_permissions rp ON r.name = rp.role_name " +
                "LEFT JOIN permission p ON rp.permissions_name = p.name " +
                "WHERE u.username = ?";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    Long userId = rs.getLong("user_id");
                    user = userMap.get(userId);
                    if (user == null) {
                        user = new User();
                        user.setUserId(rs.getLong("user_id"));
                        user.setUsername(rs.getString("username"));
                        user.setPassword(rs.getString("password"));
                        user.setEmail(rs.getString("email"));
                        user.setFullName(rs.getString("full_name"));
                        user.setPhone(rs.getString("phone"));
                        user.setAvatarUrl(rs.getString("avatar_url"));

                        Date date = rs.getDate("date_of_birth");
                        if (date != null) {
                            user.setDateOfBirth(date.toLocalDate());
                        } else {
                            user.setDateOfBirth(null);
                        }

                        String gender = rs.getString("gender");
                        if (gender != null) {
                            user.setGender(Gender.valueOf(gender));
                        } else {
                            user.setGender(null);
                        }

                        String accountStatus = rs.getString("account_status");
                        if (accountStatus != null) {
                            user.setAccountStatus(AccountStatus.valueOf(accountStatus));
                        } else {
                            user.setAccountStatus(null);
                        }
                        userMap.put(userId, user);
                    }
                    String roleName = rs.getString("roles_name");
                    String roleDescription = rs.getString("role_description");
                    if (roleName != null && roleDescription != null) {
                        Role role = roleMap.get(roleName);
                        if(role == null){
                            Set<Permission> permissions = new HashSet<>();
                            role = new Role(roleName, roleDescription, permissions);
                            roleMap.put(roleName, role);
                            user.getRoles().add(role);
                        }

                        String permissionName = rs.getString("permissions_name");
                        String permissionDescription = rs.getString("permission_description");
                        if(permissionName != null && permissionDescription != null){
                            Permission permission = new Permission();
                            permission.setName(permissionName);
                            permission.setDescription(permissionDescription);
                            role.getPermissions().add(permission);
                        }
                    }
                }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User createUser(User user) {
        String insertUserSql = "INSERT INTO users (username, email, password, full_name, phone, avatar_url, date_of_birth, gender, account_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String checkRoleSql = "SELECT name FROM role WHERE name IN (" +
                String.join(",", Collections.nCopies(user.getRoles() != null ? user.getRoles().size() : 0, "?")) + ")";
        String insertUserRoleSql = "INSERT INTO user_roles (user_id, roles_name) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement userStmt = conn.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement checkRoleStmt = user.getRoles() != null && !user.getRoles().isEmpty() ? conn.prepareStatement(checkRoleSql) : null;
                 PreparedStatement userRoleStmt = user.getRoles() != null && !user.getRoles().isEmpty() ? conn.prepareStatement(insertUserRoleSql) : null) {

                userStmt.setString(1, user.getUsername());
                userStmt.setString(2, user.getEmail());
                userStmt.setString(3, user.getPassword());
                userStmt.setString(4, user.getFullName());
                userStmt.setString(5, user.getPhone());
                userStmt.setString(6, user.getAvatarUrl());
                if (user.getDateOfBirth() != null) {
                    userStmt.setDate(7, Date.valueOf(user.getDateOfBirth()));
                } else {
                    userStmt.setNull(7, Types.DATE);
                }
                if (user.getGender() != null) {
                    userStmt.setString(8, user.getGender().name());
                } else {
                    userStmt.setNull(8, Types.VARCHAR);
                }
                if (user.getAccountStatus() != null) {
                    userStmt.setString(9, user.getAccountStatus().name());
                } else {
                    userStmt.setNull(9, Types.VARCHAR);
                }
                userStmt.executeUpdate();

                // Lấy user_id được tạo
                try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }

                // Kiểm tra và lưu quan hệ User-Role
                if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                    // Kiểm tra vai trò tồn tại
                    int index = 1;
                    for (Role role : user.getRoles()) {
                        checkRoleStmt.setString(index++, role.getName());
                    }

                    ResultSet rs = checkRoleStmt.executeQuery();
                    Set<String> foundRoles = new HashSet<>();
                    while (rs.next()) {
                        foundRoles.add(rs.getString("name"));
                    }
                    rs.close();
                    if (foundRoles.size() != user.getRoles().size()) {
                        throw new SQLException("Some roles do not exist: " +
                                user.getRoles().stream().filter(r -> !foundRoles.contains(r)).collect(Collectors.toSet()));
                    }

                    // Chèn vào user_roles
                    for (Role role : user.getRoles()) {
                        userRoleStmt.setLong(1, user.getUserId());
                        userRoleStmt.setString(2, role.getName());
                        userRoleStmt.addBatch();
                    }
                    userRoleStmt.executeBatch();
                } else {
                    System.out.println("No roles provided for user: " + user.getUsername());
                }

                conn.commit();
                return user;
            } catch (SQLException e) {
                System.err.println("SQL Error: " + e.getMessage());
                e.printStackTrace();
                conn.rollback();
                throw new RuntimeException("Error creating user: " + e.getMessage(), e);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error establishing connection: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateUser(User user) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false); // Bật giao dịch

            List<String> columns = new ArrayList<>();
            List<Object> values = new ArrayList<>();

            if (user.getUsername() != null) {
                columns.add("username = ?");
                values.add(user.getUsername());
            }
            if (user.getEmail() != null) {
                columns.add("email = ?");
                values.add(user.getEmail());
            }
            if (user.getPassword() != null) {
                columns.add("password = ?");
                values.add(user.getPassword());
            }
            if (user.getFullName() != null) {
                columns.add("full_name = ?");
                values.add(user.getFullName());
            }
            if (user.getPhone() != null) {
                columns.add("phone = ?");
                values.add(user.getPhone());
            }
            if (user.getAvatarUrl() != null) {
                columns.add("avatar_url = ?");
                values.add(user.getAvatarUrl());
            }
            if (user.getDateOfBirth() != null) {
                columns.add("date_of_birth = ?");
                values.add(Date.valueOf(user.getDateOfBirth()));
            }
            columns.add("gender = ?");
            values.add(user.getGender() != null ? user.getGender().name() : null);
            columns.add("account_status = ?");
            values.add(user.getAccountStatus() != null ? user.getAccountStatus().name() : null);

            if (!columns.isEmpty()) {
                String sqlUsers = "UPDATE users SET " + String.join(", ", columns) + " WHERE user_id = ?";
                values.add(user.getUserId());

                try (PreparedStatement stmt = conn.prepareStatement(sqlUsers)) {
                    for (int i = 0; i < values.size(); i++) {
                        stmt.setObject(i + 1, values.get(i));
                    }
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected == 0) {
                        throw new SQLException("User with ID " + user.getUserId() + " not found");
                    }
                }
            }

            String deleteRolesSql = "DELETE FROM user_roles WHERE user_id = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteRolesSql)) {
                deleteStmt.setLong(1, user.getUserId());
                deleteStmt.executeUpdate();
            }

            if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                String sqlUserRoles = "INSERT INTO user_roles (user_id, roles_name) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sqlUserRoles)) {
                    for (Role role : user.getRoles()) {
                        stmt.setLong(1, user.getUserId());
                        stmt.setString(2, role.getName());
                        stmt.addBatch();
                    }
                    stmt.executeBatch();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Rollback failed: " + rollbackEx.getMessage());
                    rollbackEx.printStackTrace();
                    throw new RuntimeException("Rollback failed: " + rollbackEx.getMessage(), rollbackEx);
                }
            }
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeEx) {
                    System.err.println("Failed to close connection: " + closeEx.getMessage());
                    closeEx.printStackTrace();
                    throw new RuntimeException("Failed to close connection: " + closeEx.getMessage(), closeEx);
                }
            }
        }
    }

    @Override
    public void deleteUser(long userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isUserExistsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try(Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean isUserExistsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean isUserExistsById(String userId) {
        String sql = "SELECT COUNT(*) FROM users WHERE user_id = ?";
    try(Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
