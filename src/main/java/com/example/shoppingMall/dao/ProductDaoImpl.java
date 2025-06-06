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
        String sql = "SELECT p.* , pi.image_url FROM products p "+
                "LEFT JOIN product_images pi ON p.product_id = pi.product_id AND pi.is_primary = 1 " +
                "LIMIT ? OFFSET ?";
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
                product.setProductImage(rs.getString("image_url"));
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
        String sql = "INSERT INTO products (seller_id, category_id, product_name, price, original_price, discount, stock_quantity, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, product.getSellerId());
            stmt.setLong(2, product.getCategoryId());
            stmt.setString(3, product.getProductName());
            stmt.setDouble(4, product.getPrice());
            stmt.setDouble(5, product.getOriginalPrice());
            stmt.setDouble(6, product.getDiscount());
            stmt.setLong(7, product.getStockQuantity());
            stmt.setString(8, product.getProductStatus().name());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    product.setProductId(generatedKeys.getLong(1));
                }

            }
            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product updateProduct(long productId, Product product) {

        return null;
    }

    @Override
    public void deleteProduct(long productId) {
        String sql = "DELETE FROM products WHERE product_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
