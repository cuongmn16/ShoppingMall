package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.ProductVariation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * JDBC implementation for product variation CRUD & query.
 * <p>
 *  Tables involved
 *  ----------------
 *   - product_variation (product_variation_id PK)
 *   - product_variation_option_value (variation_id, option_value_id)
 *   - option_value (option_value_id, option_type_id, value)
 *   - option_type  (option_type_id, name)
 */
@Repository
public class ProductVariationDaoImpl implements ProductVariationDao {

    @Autowired
    private DataSource dataSource;

    /* -------------------------------------------------- public api -------------------------------------------------- */

    /** lấy tất cả biến thể + optionInputs cho 1 sản phẩm */
    @Override
    public List<ProductVariation> findByProductId(long productId) {
        final String sql = """
                SELECT product_variation_id, product_id, sku, price, quantity
                FROM product_variation
                WHERE product_id = ?
                """;
        List<ProductVariation> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, productId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProductVariation v = new ProductVariation();
                v.setVariationId(rs.getLong("product_variation_id"));
                v.setProductId(rs.getLong("product_id"));
                v.setSku(rs.getString("sku"));
                v.setPrice(rs.getDouble("price"));
                v.setQuantity(rs.getLong("quantity"));
                v.setOptionInputs(fetchOptionInputs(conn, v.getVariationId()));
                list.add(v);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching variations", e);
        }
        return list;
    }

    /** trả về map<optionType, list<optionValue>> cho toàn product */
    @Override
    public Map<String, List<String>> getOptionInputsOfProduct(long productId) {
        final String sql = """
                SELECT ot.name AS type_name, ov.value AS option_value
                FROM product_variation pv
                JOIN product_variation_option_value pvov ON pvov.product_variation_id = pv.product_variation_id
                JOIN option_value ov ON ov.option_value_id = pvov.option_value_id
                JOIN option_type ot ON ot.option_type_id = ov.option_type_id
                WHERE pv.product_id = ?
                """;
        Map<String, Set<String>> tmp = new LinkedHashMap<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, productId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String type = rs.getString("type_name");
                String value = rs.getString("option_value");
                tmp.computeIfAbsent(type, k -> new LinkedHashSet<>()).add(value);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading option inputs", e);
        }
        Map<String, List<String>> res = new LinkedHashMap<>();
        tmp.forEach((k, v) -> res.put(k, new ArrayList<>(v)));
        return res;
    }

    /** thêm mới 1 variation (bao gồm optionInputs) */
    @Override
    public ProductVariation addVariation(ProductVariation variation) {
        String insertVarSql = "INSERT INTO product_variation (product_id, sku, price, quantity) VALUES (?,?,?,?)";
        String insertPvovSql = "INSERT INTO product_variation_option_value (product_variation_id, option_value_id) VALUES (?,?)";
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            // 1. insert variation
            try (PreparedStatement varStmt = conn.prepareStatement(insertVarSql, Statement.RETURN_GENERATED_KEYS)) {
                varStmt.setLong(1, variation.getProductId());
                varStmt.setString(2, variation.getSku());
                varStmt.setDouble(3, variation.getPrice());
                varStmt.setLong(4, variation.getQuantity());
                varStmt.executeUpdate();
                try (ResultSet keys = varStmt.getGeneratedKeys()) {
                    if (keys.next()) variation.setVariationId(keys.getLong(1));
                }
            }

            // 2. link option_value
            try (PreparedStatement pvovStmt = conn.prepareStatement(insertPvovSql)) {
                for (String input : variation.getOptionInputs()) {
                    String[] parts = input.split(":");
                    if (parts.length != 2) continue;
                    long optionValueId = getOrCreateOptionValue(conn, parts[0].trim(), parts[1].trim());
                    pvovStmt.setLong(1, variation.getVariationId());
                    pvovStmt.setLong(2, optionValueId);
                    pvovStmt.addBatch();
                }
                pvovStmt.executeBatch();
            }
            conn.commit();
            return variation;
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting variation", e);
        }
    }

    @Override
    public ProductVariation updateVariation(long variationId, ProductVariation variation) {
        String updSql = "UPDATE product_variation SET sku = ?, price = ?, quantity = ? WHERE product_variation_id = ?";
        String delPvov = "DELETE FROM product_variation_option_value WHERE product_variation_id = ?";
        String insPvov = "INSERT INTO product_variation_option_value (product_variation_id, option_value_id) VALUES (?,?)";
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement upd = conn.prepareStatement(updSql)) {
                upd.setString(1, variation.getSku());
                upd.setDouble(2, variation.getPrice());
                upd.setLong(3, variation.getQuantity());
                upd.setLong(4, variationId);
                upd.executeUpdate();
            }
            try (PreparedStatement del = conn.prepareStatement(delPvov)) {
                del.setLong(1, variationId);
                del.executeUpdate();
            }
            try (PreparedStatement ins = conn.prepareStatement(insPvov)) {
                for (String input : variation.getOptionInputs()) {
                    String[] parts = input.split(":");
                    if (parts.length != 2) continue;
                    long optionValueId = getOrCreateOptionValue(conn, parts[0].trim(), parts[1].trim());
                    ins.setLong(1, variationId);
                    ins.setLong(2, optionValueId);
                    ins.addBatch();
                }
                ins.executeBatch();
            }
            conn.commit();
            variation.setVariationId(variationId);
            return variation;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating variation", e);
        }
    }

    @Override
    public void deleteVariation(long variationId) {
        String delPvov = "DELETE FROM product_variation_option_value WHERE product_variation_id = ?";
        String delVar = "DELETE FROM product_variation WHERE product_variation_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pvovStmt = conn.prepareStatement(delPvov);
             PreparedStatement varStmt = conn.prepareStatement(delVar)) {
            pvovStmt.setLong(1, variationId);
            pvovStmt.executeUpdate();
            varStmt.setLong(1, variationId);
            varStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting variation", e);
        }
    }

    /* ----------------------------- helper methods ----------------------------- */

    /** Lấy list "type:value" cho 1 variation */
    @Override
    public List<String> fetchOptionInputs(Connection conn, long variationId) throws SQLException {
        final String sql = """
                SELECT ot.name AS type_name, ov.value AS option_value
                FROM product_variation_option_value pvov
                JOIN option_value ov ON ov.option_value_id = pvov.option_value_id
                JOIN option_type ot ON ot.option_type_id = ov.option_type_id
                WHERE pvov.product_variation_id = ?
                """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, variationId);
            ResultSet rs = stmt.executeQuery();
            List<String> list = new ArrayList<>();
            while (rs.next()) {
                list.add(rs.getString("type_name") + ":" + rs.getString("option_value"));
            }
            return list;
        }
    }

    /** lấy hoặc chèn option_type & option_value, trả về id của option_value */
    private long getOrCreateOptionValue(Connection conn, String type, String value) throws SQLException {
        long typeId = getOrCreateOptionType(conn, type);
        // 1) thử lấy option_value
        String selectVal = "SELECT option_value_id FROM option_value WHERE option_type_id = ? AND value = ?";
        try (PreparedStatement stmt = conn.prepareStatement(selectVal)) {
            stmt.setLong(1, typeId);
            stmt.setString(2, value);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getLong(1);
        }
        // 2) insert nếu chưa có
        String insertVal = "INSERT INTO option_value (option_type_id, value) VALUES (?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertVal, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, typeId);
            stmt.setString(2, value);
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) return keys.getLong(1);
        }
        throw new SQLException("Cannot insert option_value");
    }

    private long getOrCreateOptionType(Connection conn, String type) throws SQLException {
        String selectType = "SELECT option_type_id FROM option_type WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(selectType)) {
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getLong(1);
        }
        String insertType = "INSERT INTO option_type (name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertType, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, type);
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) return keys.getLong(1);
        }
        throw new SQLException("Cannot insert option_type");
    }
}
