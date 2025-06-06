package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.ProductImages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
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
    public ProductImages addProductImage(long productId, ProductImages productImages) {
        return null;
    }

    @Override
    public ProductImages updateProductImage(long productId, long imageId, ProductImages productImages) {
        return null;
    }

    @Override
    public void deleteProductImage(long productId, long imageId) {

    }
}
