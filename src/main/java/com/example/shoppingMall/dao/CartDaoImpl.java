package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CartDaoImpl implements CartDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<Cart> getAllCarts(long userId) {
        return List.of();
    }

    @Override
    public Cart addToCart(String username, Cart cart) {
        String sql = "INSERT INTO cart (username, product_id, quantity, variation_id) VALUES (?, ?, ?, ?)";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, username);
            stmt.setLong(2, cart.getProductId());
            stmt.setInt(3, cart.getQuantity());
            stmt.setLong(4, cart.getVariationId());

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cart.setCartId(rs.getLong(1));
            }
            stmt.executeUpdate();

            return cart;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
