package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.ProductImages;
import com.example.shoppingMall.model.ProductVariations;
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
public class ProductVariationsDaoImpl implements ProductVariationsDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<ProductVariations> getAllProductVariations(long productId) {
        String sql = "SELECT * FROM product_variations WHERE product_id = ?";
        List<ProductVariations> variations = new ArrayList<>();
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, productId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                ProductVariations variation = new ProductVariations();
                variation.setVariationId(rs.getLong("variation_id"));
                variation.setProductId(rs.getLong("product_id"));
                variation.setVariationName(rs.getString("variation_name"));
                variation.setPriceAdjustment(rs.getDouble("price_adjustment"));
                variation.setStockQuantity(rs.getInt("stock_quantity"));
                variation.setImageUrl(rs.getString("image_url"));

                variations.add(variation);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return variations;
    }

    @Override
    public ProductVariations addProductVariation(long productId, ProductVariations productVariations) {
        return null;
    }

    @Override
    public ProductVariations updateProductVariation(long productId, long variationId, ProductVariations productVariations) {
        return null;
    }

    @Override
    public void deleteProductVariation(long productId, long variationId) {

    }
}
