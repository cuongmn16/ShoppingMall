package com.example.shoppingMall.admin.adminDao;

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
public class ManageProductDaoImpl implements  ManageProductDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<Product> getAllProductsBySellerId(long sellerId, String search, String category, String stock) {
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
        SELECT 
            p.product_id, p.seller_id, p.category_id, p.product_name, 
            p.description, p.price, p.original_price, p.discount,
            p.stock_quantity, p.sold_quantity, p.rating, p.status,
            pi.image_url
        FROM products p
        LEFT JOIN product_images pi ON p.product_id = pi.product_id AND pi.is_primary = 1
        WHERE 1=1 
    """);

        List<Object> params = new ArrayList<>();
        sql.append(" AND p.seller_id = ?");
        params.add(sellerId); // Thêm sellerId vào danh sách tham số

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (LOWER(p.product_name) LIKE ? OR LOWER(p.description) LIKE ?)");
            params.add("%" + search.toLowerCase() + "%");
            params.add("%" + search.toLowerCase() + "%");
        }
        if (category != null && !category.isEmpty()) {
            sql.append(" AND p.category_id = ?");
            params.add(Long.parseLong(category)); // Giả sử categoryId là Long
        }
        if (stock != null && !stock.isEmpty()) {
            if ("low".equals(stock)) sql.append(" AND p.stock_quantity < 10");
            else if ("medium".equals(stock)) sql.append(" AND p.stock_quantity BETWEEN 10 AND 50");
            else if ("high".equals(stock)) sql.append(" AND p.stock_quantity > 50");
        }

        sql.append(" GROUP BY p.product_id, p.seller_id, p.category_id, p.product_name, p.description, p.price, p.original_price, p.discount, p.stock_quantity, p.sold_quantity, p.rating, p.status, pi.image_url");
        sql.append(" ORDER BY COALESCE(SUM(p.sold_quantity), 0) DESC");


        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getLong("product_id"));
                    product.setSellerId(rs.getLong("seller_id"));
                    product.setCategoryId(rs.getLong("category_id"));
                    product.setProductName(rs.getString("product_name"));
                    product.setDescription(rs.getString("description"));
                    product.setPrice(rs.getDouble("price"));
                    product.setOriginalPrice(rs.getDouble("original_price"));
                    product.setDiscount(rs.getDouble("discount"));
                    product.setStockQuantity(rs.getInt("stock_quantity"));
                    product.setSoldQuantity(rs.getInt("sold_quantity"));
                    product.setRating(rs.getDouble("rating"));
                    String productStatus = rs.getString("status");
                    if (productStatus != null) {
                        product.setProductStatus(ProductStatus.valueOf(productStatus));
                    } else {
                        product.setProductStatus(ProductStatus.ACTIVE);
                    }
                    product.setProductImage(rs.getString("image_url"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
