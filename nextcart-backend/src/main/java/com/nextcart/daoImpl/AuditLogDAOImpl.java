package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.AuditLogDAO;
import com.nextcart.model.AuditLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuditLogDAOImpl implements AuditLogDAO {

    @Override
    public Optional<AuditLog> findById(Integer id) {
        String sql = "SELECT * FROM audit_logs WHERE audit_log_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractAuditLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<AuditLog> findAll() {
        List<AuditLog> auditLogs = new ArrayList<>();
        String sql = "SELECT * FROM audit_logs ORDER BY created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                auditLogs.add(extractAuditLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auditLogs;
    }

    @Override
    public List<AuditLog> findByUserId(Integer userId) {
        List<AuditLog> auditLogs = new ArrayList<>();
        String sql = "SELECT * FROM audit_logs WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                auditLogs.add(extractAuditLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auditLogs;
    }

    @Override
    public List<AuditLog> findByEntityType(String entityType) {
        List<AuditLog> auditLogs = new ArrayList<>();
        String sql = "SELECT * FROM audit_logs WHERE entity_type = ? ORDER BY created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entityType);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                auditLogs.add(extractAuditLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auditLogs;
    }

    @Override
    public AuditLog create(AuditLog auditLog) {
        String sql = "INSERT INTO audit_logs (user_id, action, entity_type, entity_id, old_value, new_value, ip_address, user_agent) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (auditLog.getUserId() != null) {
                pstmt.setInt(1, auditLog.getUserId());
            } else {
                pstmt.setNull(1, Types.INTEGER);
            }
            pstmt.setString(2, auditLog.getAction());
            pstmt.setString(3, auditLog.getEntityType());
            if (auditLog.getEntityId() != null) {
                pstmt.setInt(4, auditLog.getEntityId());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }
            pstmt.setString(5, auditLog.getOldValue());
            pstmt.setString(6, auditLog.getNewValue());
            pstmt.setString(7, auditLog.getIpAddress());
            pstmt.setString(8, auditLog.getUserAgent());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    auditLog.setAuditLogId(rs.getInt(1));
                    return findById(auditLog.getAuditLogId()).orElse(auditLog);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private AuditLog extractAuditLog(ResultSet rs) throws SQLException {
        AuditLog auditLog = new AuditLog();
        auditLog.setAuditLogId(rs.getInt("audit_log_id"));
        int userId = rs.getInt("user_id");
        if (!rs.wasNull()) {
            auditLog.setUserId(userId);
        }
        auditLog.setAction(rs.getString("action"));
        auditLog.setEntityType(rs.getString("entity_type"));
        int entityId = rs.getInt("entity_id");
        if (!rs.wasNull()) {
            auditLog.setEntityId(entityId);
        }
        auditLog.setOldValue(rs.getString("old_value"));
        auditLog.setNewValue(rs.getString("new_value"));
        auditLog.setIpAddress(rs.getString("ip_address"));
        auditLog.setUserAgent(rs.getString("user_agent"));
        auditLog.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return auditLog;
    }
}
