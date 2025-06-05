package com.example.shoppingMall.dao;

import com.example.shoppingMall.enums.ProductStatus;
import com.example.shoppingMall.model.Product;
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
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private DataSource dataSource;


    @Override
    public List<Product> getAllProducts(int pageNumber, int pageSize) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products LIMIT ? OFFSET ?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            int offset = (pageNumber - 1) * pageSize;
            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getLong("product_id"));
                product.setSellerId(rs.getLong("seller_id"));
                product.setCategoryId(rs.getLong("category_id"));
                product.setProductName(rs.getString("product_name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setDiscount(rs.getDouble("discount"));
                product.setStockQuantity(rs.getInt("stock_quantity"));
                product.setSoldQuantity(rs.getInt("sold_quantity"));
                product.setRating(rs.getDouble("rating"));

                String productStatus = rs.getString("product_status");
                if(productStatus != null) {
                    product.setProductStatus(ProductStatus.valueOf(productStatus));
                } else {
                    product.setProductStatus(ProductStatus.ACTIVE);
                }
                products.add(product);

            }
            return products;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAllProductsByCategory(long categoryId) {
        return List.of();
    }

    @Override
    public Product getProductById(long productId) {
        return null;
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(long productId, Product product) {
        return null;
    }

    @Override
    public void deleteProduct(long productId) {

    }
}
