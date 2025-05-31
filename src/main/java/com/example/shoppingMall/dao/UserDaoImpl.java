package com.example.shoppingMall.dao;

import com.example.shoppingMall.enums.AccountStatus;
import com.example.shoppingMall.enums.Gender;
import com.example.shoppingMall.model.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = " select * from users";
        try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
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

                users.add(user);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User getUserById(long userId) {
        User user = null;
        String sql = " select * from users where user_id = ?";
        try(Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
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

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = null;
        String sql = " select * from users where username = ?";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
                while(rs.next()){
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

                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User createUser(User user) {
        String sql = "INSERT INTO users (username, email, password, full_name, phone, avatar_url, date_of_birth, gender, account_status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
                }
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(long userId) {

    }

    @Override
    public boolean isUserExistsById(String username) {
        return false;
    }

    @Override
    public boolean isUserExistsByUsername(String username) {
        return false;
    }
}
