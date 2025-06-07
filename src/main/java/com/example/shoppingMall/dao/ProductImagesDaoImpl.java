package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.ProductImages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class ProductImagesDaoImpl implements ProductImagesDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<ProductImages> getAllProductImages(long productId) {
        List<ProductImages> productImagesList = new ArrayList<>();
        String sql = "SELECT * FROM product_images WHERE product_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, productId);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                ProductImages productImages = new ProductImages();
                productImages.setImageId(result.getLong("image_id"));
                productImages.setProductId(result.getLong("product_id"));
                productImages.setImageUrl(result.getString("image_url"));
                productImages.setPrimary(result.getBoolean("is_primary"));
                productImages.setDisplayOrder(result.getLong("display_order"));

                productImagesList.add(productImages);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return productImagesList;
    }
    @Override
    public void addProductImage(long productId, ProductImages productImages) {
        String sql = "INSERT INTO product_images (product_id, image_url, is_primary, display_order) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, productId );
            stmt.setString(2, productImages.getImageUrl());
            stmt.setBoolean(3, productImages.getPrimary());
            stmt.setLong(4, productImages.getDisplayOrder());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductImages updateProductImage(long productId, long imageId, ProductImages productImages) {
        String sql = "UPDATE product_images SET image_url = ?, is_primary = ?, display_order = ? WHERE product_id = ? AND image_id = ?";
        try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productImages.getImageUrl());
            stmt.setBoolean(2, productImages.getPrimary());
            stmt.setLong(3, productImages.getDisplayOrder());
            stmt.setLong(4, productId);
            stmt.setLong(5, imageId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void deleteProductImage(long productId, long imageId) {
        String sql = "DELETE FROM product_images WHERE product_id = ? AND image_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, productId);
            stmt.setLong(2, imageId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductImages getProductImageById(long productId, long imageId) {
        String sql = "SELECT * FROM product_images WHERE product_id = ? AND image_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, productId);
            stmt.setLong(2, imageId);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                ProductImages productImages = new ProductImages();
                productImages.setImageId(resultSet.getLong("image_id"));
                productImages.setProductId(resultSet.getLong("product_id"));
                productImages.setImageUrl(resultSet.getString("image_url"));
                productImages.setPrimary(resultSet.getBoolean("is_primary"));
                productImages.setDisplayOrder(resultSet.getLong("display_order"));
                return productImages;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean isProductImageExistsById(long productId, long imageId) {
        String sql = "SELECT COUNT(*) FROM product_images WHERE product_id = ? AND image_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, productId);
            stmt.setLong(2, imageId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking if product image exists", e);
        }
        return false;
    }

}
