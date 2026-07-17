package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.ExchangeRequestDAO;
import com.nextcart.model.ExchangeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRequestDAOImpl implements ExchangeRequestDAO {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRequestDAOImpl.class);

    @Override
    public Optional<ExchangeRequest> findById(Integer exchangeId) {
        String sql = "SELECT * FROM exchange_requests WHERE exchange_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, exchangeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractExchangeRequest(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding exchange request by id: " + exchangeId, e);
        }

        return Optional.empty();
    }

    @Override
    public List<ExchangeRequest> findAll() {
        List<ExchangeRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM exchange_requests ORDER BY created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                requests.add(extractExchangeRequest(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding all exchange requests", e);
        }

        return requests;
    }

    @Override
    public List<ExchangeRequest> findByStatus(String status) {
        List<ExchangeRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM exchange_requests WHERE status = ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                requests.add(extractExchangeRequest(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding exchange requests by status: " + status, e);
        }

        return requests;
    }

    @Override
    public ExchangeRequest create(ExchangeRequest exchangeRequest) {
        String sql = "INSERT INTO exchange_requests (order_item_id, reason, remarks, status, approved_by) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, exchangeRequest.getOrderItemId());
            stmt.setString(2, exchangeRequest.getReason());
            stmt.setString(3, exchangeRequest.getRemarks());
            stmt.setString(4, exchangeRequest.getStatus() != null ? exchangeRequest.getStatus() : "PENDING");
            stmt.setInt(5, exchangeRequest.getApprovedBy() != null ? exchangeRequest.getApprovedBy() : 0);
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                exchangeRequest.setExchangeId(rs.getInt(1));
            }
            
            return findById(exchangeRequest.getExchangeId()).orElse(exchangeRequest);
        } catch (SQLException e) {
            logger.error("Error creating exchange request", e);
            return exchangeRequest;
        }
    }

    @Override
    public ExchangeRequest update(ExchangeRequest exchangeRequest) {
        String sql = "UPDATE exchange_requests SET order_item_id = ?, reason = ?, remarks = ?, status = ?, approved_by = ? WHERE exchange_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, exchangeRequest.getOrderItemId());
            stmt.setString(2, exchangeRequest.getReason());
            stmt.setString(3, exchangeRequest.getRemarks());
            stmt.setString(4, exchangeRequest.getStatus());
            stmt.setInt(5, exchangeRequest.getApprovedBy() != null ? exchangeRequest.getApprovedBy() : 0);
            stmt.setInt(6, exchangeRequest.getExchangeId());
            
            stmt.executeUpdate();
            return findById(exchangeRequest.getExchangeId()).orElse(exchangeRequest);
        } catch (SQLException e) {
            logger.error("Error updating exchange request", e);
            return exchangeRequest;
        }
    }

    @Override
    public void delete(Integer exchangeId) {
        String sql = "DELETE FROM exchange_requests WHERE exchange_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, exchangeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting exchange request", e);
        }
    }

    private ExchangeRequest extractExchangeRequest(ResultSet rs) throws SQLException {
        ExchangeRequest exchangeRequest = new ExchangeRequest();
        exchangeRequest.setExchangeId(rs.getInt("exchange_id"));
        exchangeRequest.setOrderItemId(rs.getInt("order_item_id"));
        exchangeRequest.setReason(rs.getString("reason"));
        exchangeRequest.setRemarks(rs.getString("remarks"));
        exchangeRequest.setStatus(rs.getString("status"));
        exchangeRequest.setApprovedBy(rs.getInt("approved_by"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            exchangeRequest.setCreatedAt(createdAt.toLocalDateTime());
        }

        return exchangeRequest;
    }
}
