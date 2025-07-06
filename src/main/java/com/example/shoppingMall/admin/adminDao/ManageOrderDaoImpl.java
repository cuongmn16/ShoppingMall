package com.example.shoppingMall.admin.adminDao;

import com.example.shoppingMall.enums.OrderStatus;
import com.example.shoppingMall.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ManageOrderDaoImpl implements ManageOrderDao {
    @Autowired
    private DataSource dataSource;


    @Override
    public List<Orders> getAllOder(long userId) {
        List<Orders> orders = new ArrayList<>();
        Map<Long, Orders> orderMap = new HashMap<>();
        Map<Long, OrderItems> orderItemMap = new HashMap<>();
        Map<Long, ProductVariation> variationMap = new HashMap<>();

        String sql = """
        SELECT 
            o.order_id, o.user_id, o.status, o.total_amount, o.shipping_fee, o.discount_amount, o.create_at, o.update_at,
            a.recipient_name, a.phone AS recipient_phone,
            a.province, a.district, a.ward, a.detail_address,
            oi.item_id, oi.quantity AS ordered_quantity,
            p.product_id,p.price, p.product_name AS product_name,
            pv.variation_id, pv.sku, pv.price AS variation_price,
            pi.image_url AS product_image,
            ot.name AS option_type,
            ov.value AS option_value
        FROM orders o
        JOIN order_items oi ON o.order_id = oi.order_id
        JOIN products p ON oi.product_id = p.product_id
        JOIN product_variation pv ON oi.variation_id = pv.variation_id
        LEFT JOIN product_images pi ON pi.product_id = p.product_id AND pi.is_primary = 1
        JOIN addresses a ON o.shipping_address_id = a.address_id
        JOIN product_variation_option_value pvov ON pv.variation_id = pvov.variation_id
        JOIN option_value ov ON pvov.option_value_id = ov.option_value_id
        JOIN option_type ot ON ov.option_type_id = ot.option_type_id
        WHERE o.user_id = ?
        ORDER BY o.order_id DESC, oi.item_id
        """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Long orderId = rs.getLong("order_id");

                // Nếu order chưa tồn tại trong map, tạo mới
                Orders order = orderMap.get(orderId);
                if (order == null) {
                    order = new Orders();
                    order.setOrderId(orderId);
                    order.setUserId(rs.getLong("user_id"));
                    String status = rs.getString("status");
                    LocalDate createAt = rs.getDate("create_at") != null ? rs.getDate("create_at").toLocalDate() : null;
                    order.setCreateAt(createAt);
                    LocalDate updateAt = rs.getDate("update_at") != null ? rs.getDate("update_at").toLocalDate() : null;
                    order.setUpdateAt(updateAt);
                    if(status != null){
                        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
                    } else {
                        order.setStatus(OrderStatus.PENDING);
                    }
                    order.setTotalAmount(rs.getBigDecimal("total_amount"));
                    order.setShippingFee(rs.getBigDecimal("shipping_fee"));
                    order.setDiscountAmount(rs.getBigDecimal("discount_amount"));

                    // Thiết lập thông tin địa chỉ
                    Addresses address = new Addresses();
                    address.setRecipientName(rs.getString("recipient_name"));
                    address.setPhone(rs.getString("recipient_phone"));
                    address.setProvince(rs.getString("province"));
                    address.setDistrict(rs.getString("district"));
                    address.setWard(rs.getString("ward"));
                    address.setDetail_address(rs.getString("detail_address"));
                    order.setShippingAddress(address);
                    order.setOrderItems(new ArrayList<>());
                    orderMap.put(orderId, order);
                    orders.add(order);
                }

                // Tạo OrderItem nếu chưa tồn tại
                Long itemId = rs.getLong("item_id");
                OrderItems item = orderItemMap.get(itemId);
                if (item == null) {
                    item = new OrderItems();
                    item.setItemId(itemId);
                    item.setQuantity(rs.getInt("ordered_quantity"));

                    // Tạo Product
                    Product product = new Product();
                    product.setProductId(rs.getLong("product_id"));
                    product.setProductName(rs.getString("product_name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setProductImage(rs.getString("product_image"));
                    item.setProduct(product);

                    // Tạo ProductVariation nếu chưa tồn tại
                    Long variationId = rs.getLong("variation_id");
                    ProductVariation variation = variationMap.get(variationId);
                    if (variation == null) {
                        variation = new ProductVariation();
                        variation.setVariationId(variationId);
                        variation.setSku(rs.getString("sku"));
                        variation.setPrice(rs.getDouble("variation_price"));
                        variation.setOptionInputs(new ArrayList<>());
                        variationMap.put(variationId, variation);
                    }
                    item.setProductVariation(variation);

                    order.getOrderItems().add(item);
                    orderItemMap.put(itemId, item);
                }

                // Thêm VariationOption
                String optionType = rs.getString("option_type");
                String optionValue = rs.getString("option_value");
                if (optionType != null && optionValue != null) {
                    VariationOption option = new VariationOption();
                    option.setType(optionType);
                    option.setValue(optionValue);
                    item.getVariationOptions().add(option);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }
}
