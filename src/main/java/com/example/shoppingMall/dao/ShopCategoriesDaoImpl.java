package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.ShopCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
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
               shopCategory.setParentId(rs.getObject("parent_id", Long.class));

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
                shopCategory.setParentId( rs.getLong("parent_id"));

            }
            return shopCategory;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<ShopCategories> getAllShopCategoriesByParentId(long parentId) {
        List<ShopCategories> shopCategories = new ArrayList<>();
        String sql = "SELECT * FROM shop_categories WHERE parent_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, parentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ShopCategories shopCategory = new ShopCategories();
                shopCategory.setCategoryId(rs.getLong("category_id"));
                shopCategory.setCategoryName(rs.getString("category_name"));
                shopCategory.setIconUrl(rs.getString("icon_url"));
                shopCategory.setActive(rs.getBoolean("is_active"));
                shopCategory.setParentId(rs.getLong("parent_id"));

                shopCategories.add(shopCategory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return shopCategories;
    }

    @Override
    public ShopCategories createShopCategory(ShopCategories shopCategory) {
        String sql = "INSERT INTO shop_categories (category_name, icon_url, is_active, parent_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, shopCategory.getCategoryName());
            stmt.setString(2, shopCategory.getIconUrl());

            if (shopCategory.getActive() == null) {
                shopCategory.setActive(true);
            }
            stmt.setBoolean(3, shopCategory.getActive());

            if (shopCategory.getParentId() != null) {
                stmt.setInt(4, shopCategory.getParentId().intValue());
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }

            stmt.executeUpdate();

            // lấy ID vừa tạo
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    shopCategory.setCategoryId(generatedId);
                } else {
                    System.out.println("Không nhận được generated key");
                }
            }

            return shopCategory;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi SQL: " + e.getMessage(), e);
        }
    }



    @Override
    public void updateShopCategory(long categoryId, ShopCategories shopCategory) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            List<String> columns = new ArrayList<>();
            List<Object> values = new ArrayList<>();

            if (shopCategory.getCategoryName() != null) {
                columns.add("category_name = ?");
                values.add(shopCategory.getCategoryName());
            }
            if (shopCategory.getIconUrl() != null) {
                columns.add("icon_url = ?");
                values.add(shopCategory.getIconUrl());
            }
            if (shopCategory.getActive() != null) {
                columns.add("is_active = ?");
                values.add(shopCategory.getActive());
            }
            if (shopCategory.getParentId() != null) {
                columns.add("parent_id = ?");
                values.add(shopCategory.getParentId());
            }

            if (!columns.isEmpty()) {
                String sql = "UPDATE shop_categories SET " + String.join(", ", columns) + " WHERE category_id = ?";
                values.add(categoryId);

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    for (int i = 0; i < values.size(); i++) {
                        stmt.setObject(i + 1, values.get(i));
                    }

                    int rowsUpdated = stmt.executeUpdate();
                    if (rowsUpdated == 0) {
                        throw new SQLException("Category not found");
                    }
                }
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Rollback failed: " + ex.getMessage());
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    @Override
    public List<ShopCategories> findCategoriesByParentNull() {
        String sql = "SELECT * FROM shop_categories WHERE parent_id IS NULL";
        List<ShopCategories> categories = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ShopCategories category = new ShopCategories();
                category.setCategoryId(rs.getLong("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                category.setIconUrl(rs.getString("icon_url"));
                category.setActive(rs.getBoolean("is_active"));
                category.setParentId(null); // vì parent_id là null

                categories.add(category);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }

}
