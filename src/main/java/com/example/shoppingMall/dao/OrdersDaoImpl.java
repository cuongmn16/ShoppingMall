package com.example.shoppingMall.dao;

import com.example.shoppingMall.enums.OrderStatus;
import com.example.shoppingMall.model.OrderItems;
import com.example.shoppingMall.model.Orders;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrdersDaoImpl implements OrdersDao {

    private final DataSource dataSource;

    public OrdersDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /* ───────────────────────── helpers ───────────────────────── */

    private Orders mapRow(ResultSet rs) throws SQLException {
        Orders order = new Orders();
        order.setOrderId(rs.getLong("order_id"));
        order.setUserId(rs.getLong("user_id"));

        long shippingAddressId = rs.getLong("shipping_address_id");
        order.setShippingAddressId(rs.wasNull() ? null : shippingAddressId);

        order.setStatus(OrderStatus.valueOf(rs.getString("status")));
        order.setTotalAmount(rs.getBigDecimal("total_amount"));
        order.setShippingFee(rs.getBigDecimal("shipping_fee"));
        order.setDiscountAmount(rs.getBigDecimal("discount_amount"));

        Timestamp ts = rs.getTimestamp("create_at");
        if (ts != null) order.setCreateAt(ts.toLocalDateTime().toLocalDate());

        return order;
    }

    private OrderItems mapItemRow(ResultSet rs) throws SQLException {
        OrderItems item = new OrderItems();
        item.setItemId(rs.getLong("item_id"));
        item.setOrderId(rs.getLong("order_id"));
        item.setProductId(rs.getLong("product_id"));
        item.setQuantity(rs.getInt("quantity"));
        item.setVariationId(rs.getLong("variation_id"));
        return item;
    }

    private void setNullableBigDecimal(PreparedStatement st, int idx, BigDecimal val) throws SQLException {
        if (val == null) st.setNull(idx, Types.DECIMAL);
        else st.setBigDecimal(idx, val);
    }

    private void rollback(Connection c) {
        try { if (c != null) c.rollback(); } catch (SQLException ignored) {}
    }

    private void close(Connection c) {
        try { if (c != null) c.close(); } catch (SQLException ignored) {}
    }

    /* ───────────────────────── SELECT ───────────────────────── */
    @Override
    public List<Orders> getAllOrders(int pageNumber, int pageSize) {
        String sql = "SELECT * FROM orders ORDER BY order_id DESC LIMIT ? OFFSET ?";
        List<Orders> list = new ArrayList<>();

        try (Connection c = dataSource.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {

            st.setInt(1, pageSize);
            st.setInt(2, (pageNumber - 1) * pageSize);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Optional<Orders> getOrderById(long orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {

            st.setLong(1, orderId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Orders> getOrdersByUserId(long userId, int pageNumber, int pageSize) {
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_id DESC LIMIT ? OFFSET ?";
        List<Orders> list = new ArrayList<>();

        try (Connection c = dataSource.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {

            st.setLong(1, userId);
            st.setInt(2, pageSize);
            st.setInt(3, (pageNumber - 1) * pageSize);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Optional<Orders> getCartByUserId(long userId) {
        String sql = "SELECT * FROM orders WHERE user_id = ? AND status = 'CART' LIMIT 1";
        try (Connection c = dataSource.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {

            st.setLong(1, userId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }


    @Override
    public Orders createOrder(Orders o) {
        String sql = """
            INSERT INTO orders(user_id, shipping_address_id, status,
                               total_amount, shipping_fee, discount_amount)
            VALUES (?,?,?,?,?,?)
            """;
        try (Connection c = dataSource.getConnection();
             PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            st.setLong(1, o.getUserId());
            if (o.getShippingAddressId() != null)
                st.setLong(2, o.getShippingAddressId());
            else
                st.setNull(2, Types.BIGINT);

            st.setString(3, o.getStatus().name());
            setNullableBigDecimal(st, 4, o.getTotalAmount());
            setNullableBigDecimal(st, 5, o.getShippingFee());
            setNullableBigDecimal(st, 6, o.getDiscountAmount());

            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) o.setOrderId(rs.getLong(1));
            }
            return o;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* ───────────────────────── UPDATE ───────────────────────── */

    @Override
    public Orders updateOrder(long orderId, Orders o) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            List<String> cols = new ArrayList<>();
            List<Object> vals = new ArrayList<>();

            if (o.getShippingAddressId() != null) {
                cols.add("shipping_address_id = ?");
                vals.add(o.getShippingAddressId());
            }
            if (o.getStatus() != null) {
                cols.add("status = ?");
                vals.add(o.getStatus().name());
            }
            if (o.getTotalAmount() != null) {
                cols.add("total_amount = ?");
                vals.add(o.getTotalAmount());
            }
            if (o.getShippingFee() != null) {
                cols.add("shipping_fee = ?");
                vals.add(o.getShippingFee());
            }
            if (o.getDiscountAmount() != null) {
                cols.add("discount_amount = ?");
                vals.add(o.getDiscountAmount());
            }

            int updatedRows = 0;
            if (!cols.isEmpty()) {
                String sql = "UPDATE orders SET " + String.join(", ", cols) + " WHERE order_id = ?";
                vals.add(orderId);

                try (PreparedStatement st = conn.prepareStatement(sql)) {
                    for (int i = 0; i < vals.size(); i++) st.setObject(i + 1, vals.get(i));
                    updatedRows = st.executeUpdate();
                }
            }

            conn.commit();

            // Nếu không có bản ghi nào được cập nhật → trả về null
            if (updatedRows == 0) return null;

            o.setOrderId(orderId);      // Bảo đảm ID đúng
            return o;                   // Trả lại entity đã cập nhật
        } catch (SQLException e) {
            rollback(conn);
            throw new RuntimeException(e);
        } finally { close(conn); }
    }

    @Override
    public boolean isOrderExists(long orderId) {
        String sql = "SELECT 1 FROM orders WHERE order_id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {
            st.setLong(1, orderId);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderItems> getOrderItemsByOrderId(long orderId) {
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        List<OrderItems> list = new ArrayList<>();

        try (Connection c = dataSource.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {

            st.setLong(1, orderId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) list.add(mapItemRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Orders getCartOrderByUserId(long userId) {
        String sql = "SELECT * FROM orders WHERE user_id = ? AND status = 'CART' LIMIT 1";
        try (Connection c = dataSource.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {

            st.setLong(1, userId);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                } else {
                    throw new RuntimeException("No cart found for user with id = " + userId);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching cart for user " + userId, e);
        }
    }

}
