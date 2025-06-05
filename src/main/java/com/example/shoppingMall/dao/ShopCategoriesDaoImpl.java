package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.ShopCategories;
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
public class ShopCategoriesDaoImpl implements ShopCategoriesDao{
    @Autowired
    private DataSource dataSource;

    @Override
    public List<ShopCategories> getAllShopCategories() {
        List<ShopCategories> shopCategories = new ArrayList<>();
        String sql = "SELECT * FROM shop_categories";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeQuery();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
               ShopCategories shopCategory = new ShopCategories();
               shopCategory.setCategoryId(rs.getLong("category_id"));
               shopCategory.setCategoryName(rs.getString("category_name"));
               shopCategory.setIconUrl(rs.getString("icon_url"));
               shopCategory.setActive(rs.getBoolean("is_active"));
               shopCategories.add(shopCategory);
            }
            return shopCategories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ShopCategories getShopCategoryById(long categoryId) {
        ShopCategories shopCategory = null;
        String sql = "SELECT * FROM shop_categories WHERE category_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                shopCategory = new ShopCategories();
                shopCategory.setCategoryId(rs.getLong("category_id"));
                shopCategory.setCategoryName(rs.getString("category_name"));
                shopCategory.setIconUrl(rs.getString("icon_url"));
                shopCategory.setActive(rs.getBoolean("is_active"));

            }
            return shopCategory;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ShopCategories addShopCategory(ShopCategories shopCategory) {
        String sql = "INSERT INTO shop_categories (category_name, icon_url, is_active) VALUES (?, ?, ?)";
        try(Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, shopCategory.getCategoryName());
            stmt.setString(2, shopCategory.getIconUrl());
            stmt.setBoolean(3, shopCategory.getActive());
            stmt.executeUpdate();
            return shopCategory;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateShopCategory(long categoryId,ShopCategories shopCategory) {
        Connection conn = null;
        try{
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            List<String> columns = new ArrayList<>();
            List<Object> values = new ArrayList<>();
            if(shopCategory.getCategoryName() != null) {
                columns.add("category_name = ?");
                values.add(shopCategory.getCategoryName());
            }
            if(shopCategory.getIconUrl() != null) {
                columns.add("icon_url = ?");
                values.add(shopCategory.getIconUrl());
            }
            if(shopCategory.getActive() != null) {
                columns.add("is_active = ?");
                values.add(shopCategory.getActive().toString());
            }
            if(!columns.isEmpty()) {
                String sql = "UPDATE shop_categories SET " + String.join(", ", columns) + " WHERE category_id = ?";
                values.add(categoryId);

                try(PreparedStatement stmt = conn.prepareStatement(sql)){
                    for(int i = 0; 1 < values.size(); i++){
                        stmt.setObject(i + 1, values.get(i));
                    }
                    int rowsUpdated = stmt.executeUpdate();
                    if(rowsUpdated ==0){
                        throw new SQLException("User not found");
                    }
                }
            }
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteShopCategory(long categoryId) {
        String sql = "DELETE FROM shop_categories WHERE category_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, categoryId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean isShopCategoryExistsById(long categoryId) {
    String sql = "SELECT COUNT(*) FROM shop_categories WHERE category_id = ?";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setLong(1, categoryId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getLong(1) > 0;
        }

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
        return false;
    }
}
