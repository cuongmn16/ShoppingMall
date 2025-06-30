package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.OrderItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderItemsDaoImpl implements OrderItemsDao {

    @Autowired
    private DataSource dataSource;

    /* ---------- mapper ---------- */
    private OrderItems mapRow(ResultSet rs) throws SQLException {
        OrderItems it = new OrderItems();
        it.setItemId(rs.getLong("item_id"));
        it.setOrderId(rs.getLong("order_id"));
        it.setProductId(rs.getLong("product_id"));
        it.setVariationId(rs.getLong("variation_id"));
        it.setQuantity(rs.getInt("quantity"));
        return it;
    }

    /* ---------- SELECT ---------- */

    @Override
    public List<OrderItems> getItemsByOrderId(long orderId) {
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        List<OrderItems> list = new ArrayList<>();
        try (Connection c = dataSource.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {
            st.setLong(1, orderId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    @Override
    public Optional<OrderItems> getItemById(long itemId) {
        String sql = "SELECT * FROM order_items WHERE item_id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {
            st.setLong(1, itemId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return Optional.empty();
    }

    /* ---------- INSERT ---------- */

    @Override
    public OrderItems createItem(OrderItems it) {
        String sql = """
      INSERT INTO order_items(order_id, product_id, variation_id, quantity)
      VALUES (?, ?, ?, ?)
    """;

        try (Connection c = dataSource.getConnection();
             PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Kiểm tra null cho orderId
            if (it.getOrderId() != null) {
                st.setLong(1, it.getOrderId());
            } else {
                st.setNull(1, Types.BIGINT);
            }

            // Kiểm tra null cho productId
            if (it.getProductId() != null) {
                st.setLong(2, it.getProductId());
            } else {
                st.setNull(2, Types.BIGINT);
            }

            // Kiểm tra null cho variationId
            if (it.getVariationId() != null) {
                st.setLong(3, it.getVariationId());
            } else {
                st.setNull(3, Types.BIGINT);
            }

            // quantity bắt buộc (nên không cần kiểm tra null nếu bạn validate trước đó)
            st.setInt(4, it.getQuantity());

            st.executeUpdate();

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    it.setItemId(rs.getLong(1));
                }
            }

            return it;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /* ---------- UPDATE ---------- */

    @Override
    public void updateItem(long itemId, OrderItems it) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            List<String> cols = new ArrayList<>();
            List<Object> vals = new ArrayList<>();

            if (it.getVariationId() != null) {
                cols.add("variation_id = ?");
                vals.add(it.getVariationId());
            }
            if (it.getProductId() != null) {
                cols.add("product_id = ?");
                vals.add(it.getProductId());
            }
            if (it.getQuantity() != 0) {
                cols.add("quantity = ?");
                vals.add(it.getQuantity());
            }

            if (!cols.isEmpty()) {
                String sql = "UPDATE order_items SET " + String.join(", ", cols) + " WHERE item_id = ?";
                vals.add(itemId);
                try (PreparedStatement st = conn.prepareStatement(sql)) {
                    for (int i = 0; i < vals.size(); i++) st.setObject(i + 1, vals.get(i));
                    if (st.executeUpdate() == 0) throw new SQLException("Item not found");
                }
            }
            conn.commit();
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ignored) {}
            throw new RuntimeException(e);
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
    }

    /* ---------- DELETE / EXISTS ---------- */

    @Override
    public void deleteItem(long itemId) {
        String sql = "DELETE FROM order_items WHERE item_id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {
            st.setLong(1, itemId);
            st.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public boolean isItemExists(long itemId) {
        String sql = "SELECT 1 FROM order_items WHERE item_id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {
            st.setLong(1, itemId);
            try (ResultSet rs = st.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}
