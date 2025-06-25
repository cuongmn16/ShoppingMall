package com.example.shoppingMall.dao;

import com.example.shoppingMall.dto.response.ProductDetailResponse;
import com.example.shoppingMall.enums.ProductStatus;
import com.example.shoppingMall.model.Product;
import com.example.shoppingMall.model.ProductImages;
import com.example.shoppingMall.model.ProductVariation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private DataSource dataSource;


    @Override
    public List<Product> getAllProducts(int pageNumber, int pageSize) {
        List<Product> products = new ArrayList<>();
        String sql = """
            SELECT 
                p.product_id, p.seller_id, p.category_id, p.product_name, 
                p.description, p.price, p.original_price, p.discount,
                p.stock_quantity, p.sold_quantity, p.rating, p.status,
                COALESCE(SUM(p.sold_quantity), 0) AS total_sold,
                pi.image_url
            FROM products p
            LEFT JOIN product_images pi ON p.product_id = pi.product_id AND pi.is_primary = 1
            GROUP BY 
                p.product_id, p.seller_id, p.category_id, p.product_name, 
                p.description, p.price, p.original_price, p.discount,
                p.stock_quantity, p.sold_quantity, p.rating, p.status,
                pi.image_url
            ORDER BY total_sold DESC
            LIMIT ? OFFSET ?
            """;
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
                product.setOriginalPrice(rs.getDouble("original_price"));
                product.setDiscount(rs.getDouble("discount"));
                product.setStockQuantity(rs.getInt("stock_quantity"));
                product.setSoldQuantity(rs.getInt("sold_quantity"));
                product.setRating(rs.getDouble("rating"));

                String productStatus = rs.getString("status");
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
    public List<Product> getAllProductsByCategory(long categoryId, int pageNumber, int pageSize) {
        List<Product> products = new ArrayList<>();
        String sql = """
            SELECT 
                p.product_id, p.seller_id, p.category_id, p.product_name, 
                p.description, p.price, p.original_price, p.discount,
                p.stock_quantity, p.sold_quantity, p.rating, p.status,
                COALESCE(SUM(p.sold_quantity), 0) AS total_sold,
                pi.image_url
            FROM products p
            LEFT JOIN product_images pi ON p.product_id = pi.product_id AND pi.is_primary = 1
            WHERE p.category_id = ?
            GROUP BY 
                p.product_id, p.seller_id, p.category_id, p.product_name, 
                p.description, p.price, p.original_price, p.discount,
                p.stock_quantity, p.sold_quantity, p.rating, p.status,
                pi.image_url
            ORDER BY total_sold DESC
            LIMIT ? OFFSET ?
            """;
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            int offset = (pageNumber - 1) * pageSize;
            stmt.setLong(1, categoryId);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, offset);

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

                String productStatus = rs.getString("status");
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
    public ProductDetailResponse getProductById(long productId) {
        ProductDetailResponse product = null;
        String sql = "SELECT p.*, s.*  FROM products p " +
                "INNER JOIN sellers s ON p.seller_id = s.seller_id" +
                " WHERE product_id = ?";
        try(Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                product = new ProductDetailResponse();
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
                product.setShopName(rs.getString("shop_name"));
                product.setShopDescription(rs.getString("shop_description"));
                product.setShopLogo(rs.getString("shop_logo"));

                String productStatus = rs.getString("status");
                if(productStatus != null) {
                    product.setProductStatus(ProductStatus.valueOf(productStatus));
                } else {
                    product.setProductStatus(ProductStatus.ACTIVE);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    @Override
    public Product addProduct(Product product) {
        String insertProductSql = "INSERT INTO products (seller_id, category_id, product_name, price, original_price, discount, stock_quantity, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String insertOptionTypeSql = "INSERT INTO option_type (name) VALUES (?) ON DUPLICATE KEY UPDATE option_type_id=option_type_id";
        String insertOptionValueSql = "INSERT INTO option_value (option_type_id, value) VALUES (?, ?) ON DUPLICATE KEY UPDATE option_value_id=LAST_INSERT_ID(option_value_id)";
        String insertProductOptionValueSql = "INSERT INTO product_option_value (product_id, option_value_id) VALUES (?, ?)";
        String insertVariationSql = "INSERT INTO product_variation (product_id, sku, price, quantity) VALUES (?, ?, ?, ?)";
        String insertVariationOptionValSql = "INSERT INTO product_variation_option_value (variation_id, option_value_id) VALUES (?, ?)";
        String insertProductImageSql = "INSERT INTO product_images (product_id, image_url, is_primary, display_order) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false); // Bắt đầu giao dịch

            // Map để lưu option_type_id và option_value_id
            Map<String, Long> optionTypeMap = new HashMap<>();
            Map<String, Long> optionValueMap = new HashMap<>();

            // Insert vào bảng products
            try (PreparedStatement productStmt = conn.prepareStatement(insertProductSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                productStmt.setLong(1, product.getSellerId());
                productStmt.setLong(2, product.getCategoryId());
                productStmt.setString(3, product.getProductName());
                productStmt.setDouble(4, product.getPrice());
                productStmt.setDouble(5, product.getOriginalPrice());
                productStmt.setDouble(6, product.getDiscount());
                productStmt.setLong(7, product.getStockQuantity());
                productStmt.setString(8, product.getProductStatus().name());

                int affectedRows = productStmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = productStmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            product.setProductId(generatedKeys.getLong(1));
                        }
                    }
                } else {
                    conn.rollback();
                    throw new SQLException("Failed to insert product");
                }
            }

            // Insert vào bảng product_images
            if (product.getProductImages() != null && !product.getProductImages().isEmpty()) {
                try (PreparedStatement imageStmt = conn.prepareStatement(insertProductImageSql)) {
                    for (ProductImages image : product.getProductImages()) {
                        imageStmt.setLong(1, product.getProductId());
                        imageStmt.setString(2, image.getImageUrl());
                        imageStmt.setBoolean(3, image.getPrimary());
                        imageStmt.setLong(4, image.getDisplayOrder());
                        imageStmt.addBatch();
                    }
                    imageStmt.executeBatch();
                }
            }

            // Tự động sinh option_type và option_value từ dữ liệu đầu vào
            Map<String, List<String>> optionInputs = product.getOptionInputs();
            if (optionInputs != null) {
                try (PreparedStatement optionTypeStmt = conn.prepareStatement(insertOptionTypeSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    for (String optionTypeName : optionInputs.keySet()) {
                        optionTypeStmt.setString(1, optionTypeName);
                        optionTypeStmt.executeUpdate();
                        try (ResultSet optionTypeKeys = optionTypeStmt.getGeneratedKeys()) {
                            if (optionTypeKeys.next()) {
                                optionTypeMap.put(optionTypeName, optionTypeKeys.getLong(1));
                            } else {
                                try (PreparedStatement selectStmt = conn.prepareStatement("SELECT option_type_id FROM option_type WHERE name = ?")) {
                                    selectStmt.setString(1, optionTypeName);
                                    try (ResultSet rs = selectStmt.executeQuery()) {
                                        if (rs.next()) {
                                            optionTypeMap.put(optionTypeName, rs.getLong("option_type_id"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                try (PreparedStatement optionValueStmt = conn.prepareStatement(insertOptionValueSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    for (Map.Entry<String, List<String>> entry : optionInputs.entrySet()) {
                        Long optionTypeId = optionTypeMap.get(entry.getKey());
                        for (String value : entry.getValue()) {
                            optionValueStmt.setLong(1, optionTypeId);
                            optionValueStmt.setString(2, value);
                            optionValueStmt.executeUpdate();
                            try (ResultSet optionValueKeys = optionValueStmt.getGeneratedKeys()) {
                                if (optionValueKeys.next()) {
                                    optionValueMap.put(entry.getKey() + "_" + value, optionValueKeys.getLong(1));
                                }
                            }
                        }
                    }
                }

                // Liên kết với product_option_value
                try (PreparedStatement optionValStmt = conn.prepareStatement(insertProductOptionValueSql)) {
                    for (Map.Entry<String, List<String>> entry : optionInputs.entrySet()) {
                        for (String value : entry.getValue()) {
                            Long optionValueId = optionValueMap.get(entry.getKey() + "_" + value);
                            if (optionValueId != null) {
                                optionValStmt.setLong(1, product.getProductId());
                                optionValStmt.setLong(2, optionValueId);
                                optionValStmt.addBatch();
                            }
                        }
                    }
                    optionValStmt.executeBatch();
                }
            }

            // Insert vào bảng product_variation
            try (PreparedStatement variationStmt = conn.prepareStatement(insertVariationSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                for (ProductVariation variation : product.getVariations()) {
                    variationStmt.setLong(1, product.getProductId());
                    variationStmt.setString(2, variation.getSku());
                    variationStmt.setDouble(3, variation.getPrice());
                    variationStmt.setLong(4, variation.getQuantity());
                    int variationRows = variationStmt.executeUpdate();
                    if (variationRows > 0) {
                        try (ResultSet variationKeys = variationStmt.getGeneratedKeys()) {
                            if (variationKeys.next()) {
                                variation.setVariationId(variationKeys.getLong(1));
                                // Liên kết với product_variation_option_val
                                try (PreparedStatement optionValStmt = conn.prepareStatement(insertVariationOptionValSql)) {
                                    for (String optionInput : variation.getOptionInputs()) {
                                        String[] parts = optionInput.split(":");
                                        if (parts.length == 2) {
                                            String type = parts[0].trim();
                                            String value = parts[1].trim();
                                            Long optionValueId = optionValueMap.get(type + "_" + value);
                                            if (optionValueId != null) {
                                                optionValStmt.setLong(1, variation.getVariationId());
                                                optionValStmt.setLong(2, optionValueId);
                                                optionValStmt.addBatch();
                                            }
                                        }
                                    }
                                    optionValStmt.executeBatch();
                                }
                            }
                        }
                    } else {
                        conn.rollback();
                        throw new SQLException("Failed to insert variation");
                    }
                }
            }

            conn.commit(); // Hoàn tất giao dịch
            return product;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException("Rollback failed", ex);
                }
            }
            throw new RuntimeException("Error adding product: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException("Error closing connection", e);
                }
            }
        }
    }


    @Override
    public Product updateProduct(long productId, Product product) {
        String sql = """
                UPDATE products 
                SET product_name = ?, 
                    description = ?, 
                    price = ?, 
                    original_price = ?, 
                    discount = ?, 
                    stock_quantity = ?, 
                    status = ? 
                WHERE product_id = ?
            """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setDouble(4, product.getOriginalPrice());
            stmt.setDouble(5, product.getDiscount());
            stmt.setLong(6, product.getStockQuantity());
            stmt.setString(7, product.getProductStatus().name());
            stmt.setLong(8, productId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Không tìm thấy sản phẩm để cập nhật với ID = " + productId);
            }

            product.setProductId(productId);
            return product;

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi cập nhật sản phẩm: " + e.getMessage(), e);
        }
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

    @Override
    public List<Product> findProductsByKeyword(String keyword) {
        final String sql = """
        SELECT 
            p.product_id, p.seller_id, p.category_id, p.product_name, 
            p.description, p.price, p.original_price, p.discount,
            p.stock_quantity, p.sold_quantity, p.rating, p.status,
            pi.image_url
        FROM products p
        LEFT JOIN product_images pi ON p.product_id = pi.product_id AND pi.is_primary = 1
        WHERE LOWER(p.product_name) LIKE ? OR LOWER(p.description) LIKE ?
        ORDER BY p.product_name
        """;

        List<Product> products = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String likePattern = "%" + keyword.toLowerCase() + "%";
            stmt.setString(1, likePattern);
            stmt.setString(2, likePattern);

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
                    product.setProductImage(rs.getString("image_url"));

                    String statusStr = rs.getString("status");
                    if (statusStr != null) {
                        product.setProductStatus(ProductStatus.valueOf(statusStr));
                    }

                    products.add(product);
                }
            }

            return products;

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tìm kiếm sản phẩm: " + e.getMessage(), e);
        }
    }

}
