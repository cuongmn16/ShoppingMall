package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.Orders;
import com.example.shoppingMall.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrdersDaoImpl implements OrdersDao {

    private final DataSource dataSource;

    private Orders mapRow(ResultSet rs) throws SQLException {
        Orders order = new Orders();
        order.setOrderId(rs.getLong("order_id"));
        order.setUserId(rs.getLong("user_id"));

        long shippingAddressId = rs.getLong("shipping_address_id");
        order.setShippingAddressId(rs.wasNull() ? null : shippingAddressId);

        order.setStatus(OrderStatus.valueOf(rs.getString("status"))); // sửa ở đây
        order.setTotalAmount(rs.getBigDecimal("total_amount"));
        order.setShippingFee(rs.getBigDecimal("shipping_fee"));
        order.setDiscountAmount(rs.getBigDecimal("discount_amount"));

        return order;
    }


    public OrdersDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Orders> getAllOrders() {
        String sql = "SELECT * FROM orders";
        List<Orders> list = new ArrayList<>();

        try (Connection c = dataSource.getConnection();
             PreparedStatement st = c.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
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
        } catch (SQLException e) { throw new RuntimeException(e); }
        return Optional.empty();
    }

    @Override
    public List<Orders> getOrdersByUserId(long userId) {
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        List<Orders> list = new ArrayList<>();
        try (Connection c = dataSource.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {
            st.setLong(1, userId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
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
        } catch (SQLException e) { throw new RuntimeException(e); }
        return Optional.empty();
    }

    /* ---------- INSERT ---------- */

    @Override
    public Orders createOrder(Orders o) {
        String sql = """
          INSERT INTO orders(user_id, shipping_address_id, status,
                             total_amount, shipping_fee, discount_amount)
          VALUES (?,?,?,?,?,?)""";
        try (Connection c = dataSource.getConnection();
             PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            st.setLong(1, o.getUserId());
            if (o.getShippingAddressId() != null)
                st.setLong(2, o.getShippingAddressId());
            else st.setNull(2, Types.BIGINT);

            st.setString(3, o.getStatus().name());
            st.setBigDecimal(4, nullable(o.getTotalAmount()));
            st.setBigDecimal(5, nullable(o.getShippingFee()));
            st.setBigDecimal(6, nullable(o.getDiscountAmount()));

            st.executeUpdate();

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) o.setOrderId(rs.getLong(1));
            }
            return o;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private BigDecimal nullable(BigDecimal v) { return v == null ? BigDecimal.ZERO : v; }

    /* ---------- UPDATE ---------- */

    @Override
    public void updateOrder(long orderId, Orders o) {
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

            if (!cols.isEmpty()) {
                String sql = "UPDATE orders SET " + String.join(", ", cols) + " WHERE order_id = ?";
                vals.add(orderId);

                try (PreparedStatement st = conn.prepareStatement(sql)) {
                    for (int i = 0; i < vals.size(); i++) st.setObject(i + 1, vals.get(i));
                    if (st.executeUpdate() == 0) throw new SQLException("Order not found");
                }
            }
            conn.commit();
        } catch (SQLException e) {
            rollback(conn);
            throw new RuntimeException(e);
        } finally { close(conn); }
    }

    /* ---------- DELETE / EXISTS ---------- */

    @Override
    public void deleteOrder(long orderId) {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {
            st.setLong(1, orderId);
            st.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public boolean isOrderExists(long orderId) {
        String sql = "SELECT 1 FROM orders WHERE order_id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {
            st.setLong(1, orderId);
            try (ResultSet rs = st.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    /* ---------- helpers ---------- */

    private void rollback(Connection c) {
        try { if (c != null) c.rollback(); } catch (SQLException ignored) {}
    }
    private void close(Connection c) {
        try { if (c != null) c.close(); } catch (SQLException ignored) {}
    }

}
