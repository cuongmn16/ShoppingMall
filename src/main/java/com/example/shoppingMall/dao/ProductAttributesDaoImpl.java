package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.ProductAttributes;
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
public class ProductAttributesDaoImpl implements ProductAttributesDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<ProductAttributes> getAllProductAttributes(long productId) {
        String sql = "SELECT * FROM product_attributes WHERE product_id = ?";
        List<ProductAttributes> productAttributes = new ArrayList<>();
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, productId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProductAttributes attribute = new ProductAttributes();
                attribute.setAttributeId(rs.getLong("attribute_id"));
                attribute.setProductId(rs.getLong("product_id"));
                attribute.setAttributeName(rs.getString("attribute_name"));
                attribute.setAttributeValue(rs.getString("attribute_value"));
                productAttributes.add(attribute);
            }
            return productAttributes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductAttributes addProductAttribute(long productId, ProductAttributes productAttributes) {
        return null;
    }

    @Override
    public ProductAttributes updateProductAttribute(long productId, long attributeId, ProductAttributes productAttributes) {
        return null;
    }

    @Override
    public void deleteProductAttribute(long productId, long attributeId) {

    }
}
