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
        String sql = "SELECT u.*, r.name, r.description FROM users u " +
                "LEFT JOIN user_roles ur ON u.user_id = ur.user_id " +
                "LEFT JOIN role r ON ur.roles_name = r.name " +
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
                String roleName = rs.getString("name");
                String roleDescription = rs.getString("description");
                if (roleName != null && roleDescription != null) {
                    Set<Permission> permissions = new HashSet<>();
                    user.getRoles().add(new Role(roleName, roleDescription,permissions));
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
        String sql = "SELECT u.*, r.name, r.description FROM users u " +
                "LEFT JOIN user_roles ur ON u.user_id = ur.user_id " +
                "LEFT JOIN role r ON ur.roles_name = r.name " +
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
                    String roleName = rs.getString("name");
                    String roleDescription = rs.getString("description");
                    if (roleName != null && roleDescription != null) {
                        Set<Permission> permissions = new HashSet<>();
                        user.getRoles().add(new Role(roleName, roleDescription,permissions));
                    }
                }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User createUser(User user) {
        try(Connection conn = dataSource.getConnection()){
            conn.setAutoCommit(false);
            String sql = "INSERT INTO users (username, email, password, full_name, phone, avatar_url, date_of_birth, gender, account_status) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                stmt.setString(4, user.getFullName());
                stmt.setString(5, user.getPhone());
                stmt.setString(6, user.getAvatarUrl());
                if (user.getDateOfBirth() != null) {
                    stmt.setDate(7, Date.valueOf(user.getDateOfBirth()));
                } else {
                    stmt.setNull(7, Types.DATE);
                }
                if (user.getGender() != null) {
                    stmt.setString(8, user.getGender().name());
                } else {
                    stmt.setNull(8, Types.VARCHAR);
                }
                if (user.getAccountStatus() != null) {
                    stmt.setString(9, user.getAccountStatus().name());
                } else {
                    stmt.setNull(9, Types.VARCHAR);
                }
                stmt.executeUpdate();
                try(ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getLong(1));
                    }else{
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
                if(user.getRoles() != null && !user.getRoles().isEmpty()){
                    String checkRoleSql = "SELECT name FROM role WHERE name IN (" +
                            String.join(",", Collections.nCopies(user.getRoles().size(), "?")) + ")";
                    try(PreparedStatement checkRoleStmt = conn.prepareStatement(checkRoleSql)) {
                        int index = 1;
                        for (Role role : user.getRoles() ) {
                            checkRoleStmt.setString(index++, role.getName());
                        }
                        ResultSet rs = checkRoleStmt.executeQuery();
                        Set<String> foundRoles = new HashSet<>();
                        while (rs.next()) {
                            foundRoles.add(rs.getString("name"));
                        }
                        if(foundRoles.size() != user.getRoles().size()){
                            throw new SQLException("Some roles do not exist in the database.");
                        }
                    }
                    String insertRoleSql = "INSERT INTO user_roles (user_id, roles_name) VALUES (?, ?)";
                    try(PreparedStatement insertRoleStmt = conn.prepareStatement(insertRoleSql)) {
                        for(Role role : user.getRoles() ){
                            insertRoleStmt.setLong(1, user.getUserId());
                            insertRoleStmt.setString(2, role.getName());
                            insertRoleStmt.addBatch();
                        }
                        stmt.executeBatch();
                    }
                }
                conn.commit();

            } catch (SQLException e) {
                if(conn != null) {
                    try {
                        conn.rollback();
                    } catch (SQLException rollbackEx) {
                        throw new RuntimeException("Failed to rollback transaction", rollbackEx);
                    }
                }
            }finally {
                if(conn != null) {
                    try{
                        conn.close();
                    } catch (SQLException closeEx) {
                        throw new RuntimeException("Failed to close connection", closeEx);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            String sqlUsers = "UPDATE users SET username = ?, email = ?, password = ?, full_name = ?, phone = ?, " +
                    "avatar_url = ?, date_of_birth = ?, gender = ?, account_status = ? WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlUsers)) {
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                stmt.setString(4, user.getFullName());
                stmt.setString(5, user.getPhone());
                stmt.setString(6, user.getAvatarUrl());
                stmt.setDate(7, user.getDateOfBirth() != null ? Date.valueOf(user.getDateOfBirth()) : null);
                stmt.setString(8, user.getGender() != null ? user.getGender().name() : null);
                stmt.setString(9, user.getAccountStatus() != null ? user.getAccountStatus().name() : null);
                stmt.setLong(10, user.getUserId());
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new IllegalArgumentException("User with ID " + user.getUserId() + " not found");
                }
            }

            // Lấy vai trò hiện tại
            Set<String> currentRoles = new HashSet<>();
            String selectRolesSql = "SELECT roles_name FROM user_roles WHERE user_id = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectRolesSql)) {
                selectStmt.setLong(1, user.getUserId());
                ResultSet rs = selectStmt.executeQuery();
                while (rs.next()) {
                    currentRoles.add(rs.getString("roles_name"));
                }
            }

            // Kết hợp vai trò hiện tại với vai trò mới
            Set<String> updatedRoles = new HashSet<>(currentRoles);
            if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                updatedRoles.addAll(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet())); // Thêm vai trò mới
            }

            // Kiểm tra vai trò tồn tại trong bảng role
            if (!updatedRoles.isEmpty()) {
                String checkRolesSql = "SELECT name FROM role WHERE name IN (" +
                        String.join(",", Collections.nCopies(updatedRoles.size(), "?")) + ")";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkRolesSql)) {
                    int index = 1;
                    for (String roleName : updatedRoles) {
                        checkStmt.setString(index++, roleName);
                    }
                    ResultSet rs = checkStmt.executeQuery();
                    Set<String> foundRoles = new HashSet<>();
                    while (rs.next()) {
                        foundRoles.add(rs.getString("name"));
                    }
                    if (foundRoles.size() != updatedRoles.size()) {
                        throw new IllegalArgumentException("Some roles not found: " +
                                updatedRoles.stream().filter(r -> !foundRoles.contains(r)).collect(Collectors.toSet()));
                    }
                }
            }

            // Xóa vai trò cũ
            String deleteRolesSql = "DELETE FROM user_roles WHERE user_id = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteRolesSql)) {
                deleteStmt.setLong(1, user.getUserId());
                deleteStmt.executeUpdate();
            }

            // Chèn danh sách vai trò mới (nếu có)
            if (!updatedRoles.isEmpty()) {
                String sqlUserRoles = "INSERT INTO user_roles (user_id, roles_name) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sqlUserRoles)) {
                    for (String roleName : updatedRoles) {
                        stmt.setLong(1, user.getUserId());
                        stmt.setString(2, roleName);
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
                    throw new RuntimeException("Rollback failed: " + rollbackEx.getMessage(), rollbackEx);
                }
            }
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeEx) {
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
