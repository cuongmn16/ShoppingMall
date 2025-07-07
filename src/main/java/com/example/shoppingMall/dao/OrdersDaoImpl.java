package com.example.shoppingMall.dao;

import com.example.shoppingMall.dto.response.OrderItemsResponse;
import com.example.shoppingMall.dto.response.OrdersResponse;
import com.example.shoppingMall.enums.OrderStatus;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.model.OrderItems;
import com.example.shoppingMall.model.Orders;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

@Repository
public class OrdersDaoImpl implements OrdersDao {

    private final DataSource dataSource;

    public OrdersDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /* ───────────────────────── helpers ───────────────────────── */

    private Orders mapOrder(ResultSet rs) throws SQLException {
        Orders order = new Orders();
        order.setOrderId(rs.getLong("order_id"));
        order.setUserId(rs.getLong("user_id"));

        long shipId = rs.getLong("shipping_address_id");
        order.setShippingAddressId(rs.wasNull() ? null : shipId);

        order.setStatus(OrderStatus.valueOf(rs.getString("status")));
        order.setTotalAmount(rs.getBigDecimal("total_amount"));
        order.setShippingFee(rs.getBigDecimal("shipping_fee"));
        order.setDiscountAmount(rs.getBigDecimal("discount_amount"));

        Timestamp ts = rs.getTimestamp("create_at");
        if (ts != null) order.setCreateAt(ts.toLocalDateTime().toLocalDate());

        // Quan trọng: luôn tạo list rỗng
        order.setOrderItems(new ArrayList<>());
        return order;
    }


    private OrderItems mapOrderItem(ResultSet rs) throws SQLException {
        OrderItems item = new OrderItems();
        item.setItemId(rs.getLong("item_id"));
        item.setOrderId(rs.getLong("order_id"));
        item.setProductId(rs.getLong("product_id"));
        item.setVariationId(rs.getLong("variation_id"));
        item.setQuantity(rs.getInt("quantity"));
        item.setUnitPrice(rs.getBigDecimal("unit_price"));   // new
        item.setTotalPrice(rs.getBigDecimal("total_price")); // new
        item.setSku(rs.getString("sku"));                    // nếu model có
        item.setProductName(rs.getString("product_name"));   // nếu model có
        return item;
    }

    private void setNullableBigDecimal(PreparedStatement st, int idx, BigDecimal val) throws SQLException {
        if (val == null) st.setNull(idx, Types.DECIMAL);
        else st.setBigDecimal(idx, val);
    }

    private void rollback(Connection c) {
        try {
            if (c != null) c.rollback();
        } catch (SQLException ignored) {
        }
    }

    private void close(Connection c) {
        try {
            if (c != null) c.close();
        } catch (SQLException ignored) {
        }
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
                while (rs.next()) list.add(mapOrder(rs));
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
                if (rs.next()) return Optional.of(mapOrder(rs));
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
        } finally {
            close(conn);
        }
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
                while (rs.next()) list.add(mapOrderItem(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Orders> findByUserUsername(String username, int limit, int offset) {

        String sql = """
                    SELECT  o.order_id,
                        o.user_id,
                        o.shipping_address_id,
                        o.status,
                        o.total_amount,          -- ✅ thêm
                        o.shipping_fee,          -- ✅ thêm
                        o.discount_amount,       -- ✅ thêm
                        o.create_at,
                        o.update_at,
                
                        /* cột item */
                        i.item_id,               -- ✅ mapOrderItem cần
                        i.product_id,            -- ✅
                        i.variation_id,          -- ✅
                        i.quantity,
                
                        /* giá / hình / tên */
                        v.price              AS unit_price,
                        (v.price*i.quantity) AS total_price,
                        v.sku,
                        p.product_name,
                        pi.image_url         AS product_image   -- (dù chưa dùng vẫn OK)
                
                FROM       orders o
                JOIN       users              u  ON u.user_id      = o.user_id
                LEFT JOIN  order_items        i  ON i.order_id     = o.order_id
                LEFT JOIN  product_variation  v  ON v.variation_id = i.variation_id
                LEFT JOIN  products           p  ON p.product_id   = i.product_id
                LEFT JOIN  product_images     pi ON pi.product_id  = p.product_id
                                                   AND pi.is_primary = 1
                WHERE      u.username = ?
                ORDER BY   o.order_id DESC
                LIMIT  ? OFFSET ?
                
                """;

        /* LinkedHashMap để: (1) tránh trùng (2) giữ thứ tự DESC */
        Map<Long, Orders> orderMap = new LinkedHashMap<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setInt(2, limit);
            ps.setInt(3, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    long orderId = rs.getLong("order_id");

                    /* 1. Lấy hoặc tạo Orders duy nhất */
                    Orders order = orderMap.computeIfAbsent(orderId, id -> {
                        try {
                            return mapOrder(rs);      // dùng hàm vừa sửa
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    /* 2. Nếu dòng hiện tại có item (LEFT JOIN có thể NULL) */
                    long itemId = rs.getLong("item_id");
                    if (!rs.wasNull()) {
                        order.getOrderItems().add(mapOrderItem(rs));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching orders for user '" + username + "'", e);
        }

        /* Trả list đã gom */
        return new ArrayList<>(orderMap.values());
    }


    public List<OrderItemsResponse> getItemDetailsWithProductInfo(long orderId) {
        String sql = """
                SELECT 
                    oi.item_id,
                    oi.order_id,
                    oi.product_id,
                    oi.variation_id,
                    oi.quantity,
                    v.price AS unit_price,
                    (v.price * oi.quantity) AS total_price,
                    v.sku,
                    p.product_name
                FROM order_items oi
                JOIN product_variation v ON oi.variation_id = v.variation_id
                JOIN products p ON oi.product_id = p.product_id
                WHERE oi.order_id = ?
                """;

        List<OrderItemsResponse> items = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItemsResponse item = new OrderItemsResponse();

                    item.setItemId(rs.getLong("item_id"));
                    item.setOrderId(rs.getLong("order_id"));
                    item.setProductId(rs.getLong("product_id"));
                    item.setVariationId(rs.getLong("variation_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getBigDecimal("unit_price"));
                    item.setTotalPrice(rs.getBigDecimal("total_price"));
                    item.setSku(rs.getString("sku"));
                    item.setProductName(rs.getString("product_name"));

                    items.add(item);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return items;
    }

}
