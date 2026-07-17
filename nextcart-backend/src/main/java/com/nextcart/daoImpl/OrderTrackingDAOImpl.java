package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.OrderTrackingDAO;
import com.nextcart.model.OrderTracking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderTrackingDAOImpl implements OrderTrackingDAO {
    private static final Logger logger = LoggerFactory.getLogger(OrderTrackingDAOImpl.class);

    @Override
    public Optional<OrderTracking> findById(Integer trackingId) {
        String sql = "SELECT * FROM order_tracking WHERE tracking_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, trackingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractOrderTracking(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding order tracking by id: " + trackingId, e);
        }

        return Optional.empty();
    }

    @Override
    public List<OrderTracking> findByOrderId(Integer orderId) {
        List<OrderTracking> trackings = new ArrayList<>();
        String sql = "SELECT * FROM order_tracking WHERE order_id = ? ORDER BY updated_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                trackings.add(extractOrderTracking(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding order tracking by order id: " + orderId, e);
        }

        return trackings;
    }

    @Override
    public List<OrderTracking> findAll() {
        List<OrderTracking> trackings = new ArrayList<>();
        String sql = "SELECT * FROM order_tracking ORDER BY updated_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                trackings.add(extractOrderTracking(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding all order trackings", e);
        }

        return trackings;
    }

    @Override
    public OrderTracking create(OrderTracking orderTracking) {
        String sql = "INSERT INTO order_tracking (order_id, status, remarks, updated_by) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, orderTracking.getOrderId());
            stmt.setString(2, orderTracking.getStatus());
            stmt.setString(3, orderTracking.getRemarks());
            stmt.setInt(4, orderTracking.getUpdatedBy() != null ? orderTracking.getUpdatedBy() : 0);
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                orderTracking.setTrackingId(rs.getInt(1));
            }
            
            return findById(orderTracking.getTrackingId()).orElse(orderTracking);
        } catch (SQLException e) {
            logger.error("Error creating order tracking", e);
            return orderTracking;
        }
    }

    @Override
    public OrderTracking update(OrderTracking orderTracking) {
        String sql = "UPDATE order_tracking SET order_id = ?, status = ?, remarks = ?, updated_by = ? WHERE tracking_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, orderTracking.getOrderId());
            stmt.setString(2, orderTracking.getStatus());
            stmt.setString(3, orderTracking.getRemarks());
            stmt.setInt(4, orderTracking.getUpdatedBy());
            stmt.setInt(5, orderTracking.getTrackingId());
            
            stmt.executeUpdate();
            return findById(orderTracking.getTrackingId()).orElse(orderTracking);
        } catch (SQLException e) {
            logger.error("Error updating order tracking", e);
            return orderTracking;
        }
    }

    @Override
    public void delete(Integer trackingId) {
        String sql = "DELETE FROM order_tracking WHERE tracking_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, trackingId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting order tracking", e);
        }
    }

    private OrderTracking extractOrderTracking(ResultSet rs) throws SQLException {
        OrderTracking orderTracking = new OrderTracking();
        orderTracking.setTrackingId(rs.getInt("tracking_id"));
        orderTracking.setOrderId(rs.getInt("order_id"));
        orderTracking.setStatus(rs.getString("status"));
        orderTracking.setRemarks(rs.getString("remarks"));
        orderTracking.setUpdatedBy(rs.getInt("updated_by"));

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            orderTracking.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return orderTracking;
    }
}
