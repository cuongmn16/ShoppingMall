package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.Seller;
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
public class SellerDaoImpl implements SellerDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<Seller> getAllSellers() {
        List<Seller> sellers = new ArrayList<>();
        String sql = "SELECT * FROM sellers";
        try(Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Seller seller = new Seller();
                seller.setSellerId(rs.getLong("seller_id"));
                seller.setUserId(rs.getLong("user_id"));
                seller.setShopName(rs.getString("shop_name"));
                seller.setShopDescription(rs.getString("shop_description"));
                seller.setShopLogo(rs.getString("shop_logo"));
                seller.setRating(rs.getDouble("rating"));
                seller.setTotal_Products(rs.getLong("total_products"));
                sellers.add(seller);
            }
            return sellers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Seller getSellerById(long sellerId) {
        String sql = "SELECT * FROM sellers WHERE seller_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, sellerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Seller seller = new Seller();
                seller.setSellerId(rs.getLong("seller_id"));
                seller.setUserId(rs.getLong("user_id"));
                seller.setShopName(rs.getString("shop_name"));
                seller.setShopDescription(rs.getString("shop_description"));
                seller.setShopLogo(rs.getString("shop_logo"));
                seller.setRating(rs.getDouble("rating"));
                seller.setTotal_Products(rs.getLong("total_products"));
                return seller;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Seller createSeller(Seller seller) {
        String sql = "INSERT INTO sellers (user_id, shop_name, shop_description, shop_logo) VALUES (?, ?, ?, ?)";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, seller.getUserId());
            stmt.setString(2, seller.getShopName());
            stmt.setString(3, seller.getShopDescription());
            stmt.setString(4, seller.getShopLogo());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (var generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long generatedId = generatedKeys.getLong(1);
                        seller.setSellerId(generatedId);
                        return seller;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Seller updateSeller(long sellerId, Seller seller) {
        String sql = "UPDATE sellers SET user_id = ?, shop_name = ?, shop_description = ?, shop_logo = ? WHERE seller_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, seller.getUserId());
            stmt.setString(2, seller.getShopName());
            stmt.setString(3, seller.getShopDescription());
            stmt.setString(4, seller.getShopLogo());
            stmt.setLong(5, sellerId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                seller.setSellerId(sellerId);
                return seller;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean isSellerExists(long sellerId) {
        String sql = "SELECT COUNT(*) FROM sellers WHERE seller_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, sellerId);
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
    public void deleteSeller(long sellerId) {
        String sql = "DELETE FROM sellers WHERE seller_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, sellerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
