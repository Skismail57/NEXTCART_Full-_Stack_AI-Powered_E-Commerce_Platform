package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.PaymentDAO;
import com.nextcart.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentDAOImpl implements PaymentDAO {
    private static final Logger logger = LoggerFactory.getLogger(PaymentDAOImpl.class);

    @Override
    public Optional<Payment> findById(Integer paymentId) {
        String sql = "SELECT * FROM payments WHERE payment_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, paymentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractPayment(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding payment by id: " + paymentId, e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Payment> findByOrderId(Integer orderId) {
        String sql = "SELECT * FROM payments WHERE order_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractPayment(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding payment by order id: " + orderId, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Payment> findAll() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments ORDER BY created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                payments.add(extractPayment(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding all payments", e);
        }

        return payments;
    }

    @Override
    public Payment create(Payment payment) {
        String sql = "INSERT INTO payments (order_id, payment_method, transaction_id, payment_gateway, payment_status, amount, paid_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, payment.getOrderId());
            stmt.setString(2, payment.getPaymentMethod());
            stmt.setString(3, payment.getTransactionId());
            stmt.setString(4, payment.getPaymentGateway());
            stmt.setString(5, payment.getPaymentStatus() != null ? payment.getPaymentStatus() : "PENDING");
            stmt.setBigDecimal(6, payment.getAmount());
            stmt.setTimestamp(7, payment.getPaidAt() != null ? Timestamp.valueOf(payment.getPaidAt()) : null);
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                payment.setPaymentId(rs.getInt(1));
            }
            
            return findById(payment.getPaymentId()).orElse(payment);
        } catch (SQLException e) {
            logger.error("Error creating payment", e);
            return payment;
        }
    }

    @Override
    public Payment update(Payment payment) {
        String sql = "UPDATE payments SET order_id = ?, payment_method = ?, transaction_id = ?, payment_gateway = ?, payment_status = ?, amount = ?, paid_at = ? WHERE payment_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, payment.getOrderId());
            stmt.setString(2, payment.getPaymentMethod());
            stmt.setString(3, payment.getTransactionId());
            stmt.setString(4, payment.getPaymentGateway());
            stmt.setString(5, payment.getPaymentStatus());
            stmt.setBigDecimal(6, payment.getAmount());
            stmt.setTimestamp(7, payment.getPaidAt() != null ? Timestamp.valueOf(payment.getPaidAt()) : null);
            stmt.setInt(8, payment.getPaymentId());
            
            stmt.executeUpdate();
            return findById(payment.getPaymentId()).orElse(payment);
        } catch (SQLException e) {
            logger.error("Error updating payment", e);
            return payment;
        }
    }

    @Override
    public void delete(Integer paymentId) {
        String sql = "DELETE FROM payments WHERE payment_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, paymentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting payment", e);
        }
    }

    private Payment extractPayment(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setPaymentId(rs.getInt("payment_id"));
        payment.setOrderId(rs.getInt("order_id"));
        payment.setPaymentMethod(rs.getString("payment_method"));
        payment.setTransactionId(rs.getString("transaction_id"));
        payment.setPaymentGateway(rs.getString("payment_gateway"));
        payment.setPaymentStatus(rs.getString("payment_status"));
        payment.setAmount(rs.getBigDecimal("amount"));

        Timestamp paidAt = rs.getTimestamp("paid_at");
        if (paidAt != null) {
            payment.setPaidAt(paidAt.toLocalDateTime());
        }

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            payment.setCreatedAt(createdAt.toLocalDateTime());
        }

        return payment;
    }
}
