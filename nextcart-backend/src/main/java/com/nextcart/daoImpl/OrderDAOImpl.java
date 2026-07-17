package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.OrderDAO;
import com.nextcart.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderDAOImpl implements OrderDAO {
    private static final Logger logger = LoggerFactory.getLogger(OrderDAOImpl.class);

    @Override
    public Optional<Order> findById(Integer orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractOrder(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding order by id: {}", orderId, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Order> findByOrderNumber(String orderNumber) {
        String sql = "SELECT * FROM orders WHERE order_number = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractOrder(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding order by number: {}", orderNumber, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Order> findByUserId(Integer userId, int page, int size) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, size);
            stmt.setInt(3, (page - 1) * size);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(extractOrder(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding orders by user id: {}", userId, e);
        }
        return orders;
    }

    @Override
    public List<Order> findAll(int page, int size) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY created_at DESC LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, size);
            stmt.setInt(2, (page - 1) * size);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(extractOrder(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all orders", e);
        }
        return orders;
    }

    @Override
    public Order create(Order order) {
        String orderNumber = "NX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        order.setOrderNumber(orderNumber);

        String sql = "INSERT INTO orders (order_number, user_id, address_id, coupon_id, subtotal, discount, tax, shipping_charge, grand_total, payment_status, order_status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, order.getOrderNumber());
            stmt.setInt(2, order.getUserId());
            stmt.setInt(3, order.getAddressId());
            if (order.getCouponId() != null) {
                stmt.setInt(4, order.getCouponId());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            stmt.setBigDecimal(5, order.getSubtotal());
            stmt.setBigDecimal(6, order.getDiscount());
            stmt.setBigDecimal(7, order.getTax());
            stmt.setBigDecimal(8, order.getShippingCharge());
            stmt.setBigDecimal(9, order.getGrandTotal());
            stmt.setString(10, order.getPaymentStatus() != null ? order.getPaymentStatus() : "PENDING");
            stmt.setString(11, order.getOrderStatus() != null ? order.getOrderStatus() : "PENDING");
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                order.setOrderId(rs.getInt(1));
                return findById(order.getOrderId()).orElse(order);
            }
        } catch (SQLException e) {
            logger.error("Error creating order", e);
        }
        return order;
    }

    @Override
    public Order update(Order order) {
        String sql = "UPDATE orders SET user_id = ?, address_id = ?, coupon_id = ?, subtotal = ?, discount = ?, tax = ?, shipping_charge = ?, grand_total = ?, payment_status = ?, order_status = ? WHERE order_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getUserId());
            stmt.setInt(2, order.getAddressId());
            if (order.getCouponId() != null) {
                stmt.setInt(3, order.getCouponId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setBigDecimal(4, order.getSubtotal());
            stmt.setBigDecimal(5, order.getDiscount());
            stmt.setBigDecimal(6, order.getTax());
            stmt.setBigDecimal(7, order.getShippingCharge());
            stmt.setBigDecimal(8, order.getGrandTotal());
            stmt.setString(9, order.getPaymentStatus());
            stmt.setString(10, order.getOrderStatus());
            stmt.setInt(11, order.getOrderId());
            stmt.executeUpdate();
            return findById(order.getOrderId()).orElse(order);
        } catch (SQLException e) {
            logger.error("Error updating order: {}", order.getOrderId(), e);
            return order;
        }
    }

    @Override
    public Order updatePaymentStatus(Integer orderId, String paymentStatus) {
        String sql = "UPDATE orders SET payment_status = ? WHERE order_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paymentStatus);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
            return findById(orderId).orElse(null);
        } catch (SQLException e) {
            logger.error("Error updating payment status for order: {}", orderId, e);
            return null;
        }
    }

    @Override
    public Order updateOrderStatus(Integer orderId, String orderStatus) {
        String sql = "UPDATE orders SET order_status = ? WHERE order_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderStatus);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
            return findById(orderId).orElse(null);
        } catch (SQLException e) {
            logger.error("Error updating order status for order: {}", orderId, e);
            return null;
        }
    }

    @Override
    public boolean delete(Integer orderId) {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting order: {}", orderId, e);
            return false;
        }
    }

    private Order extractOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setOrderNumber(rs.getString("order_number"));
        order.setUserId(rs.getInt("user_id"));
        order.setAddressId(rs.getInt("address_id"));
        order.setCouponId((Integer) rs.getObject("coupon_id"));
        order.setSubtotal(rs.getBigDecimal("subtotal"));
        order.setDiscount(rs.getBigDecimal("discount"));
        order.setTax(rs.getBigDecimal("tax"));
        order.setShippingCharge(rs.getBigDecimal("shipping_charge"));
        order.setGrandTotal(rs.getBigDecimal("grand_total"));
        order.setPaymentStatus(rs.getString("payment_status"));
        order.setOrderStatus(rs.getString("order_status"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            order.setCreatedAt(createdAt.toLocalDateTime());
        }
        return order;
    }
}
