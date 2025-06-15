package com.example.shoppingMall.dao;

import com.example.shoppingMall.dto.response.CartResponse;
import com.example.shoppingMall.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CartDaoImpl implements CartDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<CartResponse> getAllCarts(String username) {
        List<CartResponse> carts = new ArrayList<>();
        String sql = "SELECT c.*," +
                " p.product_id, p.product_name, p.price, " +
                "pv.image_url ,pv.variation_id , pv.variation_name, pv.price_adjustment, " +
                "(p.price + COALESCE(pv.price_adjustment, 0) AS final_price, " +
                "((p.price + COALESCE(pv.price_adjustment, 0) AS total_all_price " +
                " FROM cart c " +
                "INNER JOIN products p ON c.product_id = p.product_id " +
                "LEFT JOIN product_variations pv ON c.variation_id = pv.variation_id " +
                "WHERE c.username = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CartResponse dto = new CartResponse();
                dto.setCartId(rs.getLong("cart_id"));
                dto.setUsername(rs.getString("username"));
                dto.setProductId(rs.getLong("product_id"));
                dto.setProductName(rs.getString("product_name"));
                dto.setPrice(rs.getDouble("price"));
                dto.setVariationId(rs.getLong("variation_id"));
                dto.setVariationName(rs.getString("variation_name"));
                dto.setPriceAdjustment(rs.getDouble("price_adjustment"));
                dto.setFinalPrice(rs.getDouble("final_price"));
                dto.setTotalAllPrice(rs.getDouble("total_all_price"));
                dto.setImageUrl(rs.getString("image_url"));
                dto.setQuantity(rs.getInt("quantity"));
                carts.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return carts;
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

    @Override
    public void deleteCart(long cartId) {
        String sql = "DELETE FROM cart WHERE cart_id = ?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, cartId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
