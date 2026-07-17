package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.AdminActivityLogDAO;
import com.nextcart.model.AdminActivityLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminActivityLogDAOImpl implements AdminActivityLogDAO {
    private static final Logger logger = LoggerFactory.getLogger(AdminActivityLogDAOImpl.class);

    @Override
    public Optional<AdminActivityLog> findById(Integer logId) {
        String sql = "SELECT * FROM admin_activity_logs WHERE log_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, logId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractAdminActivityLog(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding admin activity log by id: " + logId, e);
        }

        return Optional.empty();
    }

    @Override
    public List<AdminActivityLog> findAll() {
        List<AdminActivityLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM admin_activity_logs ORDER BY created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                logs.add(extractAdminActivityLog(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding all admin activity logs", e);
        }

        return logs;
    }

    @Override
    public List<AdminActivityLog> findByAdminId(Integer adminId) {
        List<AdminActivityLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM admin_activity_logs WHERE admin_id = ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, adminId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                logs.add(extractAdminActivityLog(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding admin activity logs by admin id: " + adminId, e);
        }

        return logs;
    }

    @Override
    public AdminActivityLog create(AdminActivityLog adminActivityLog) {
        String sql = "INSERT INTO admin_activity_logs (admin_id, activity, ip_address) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, adminActivityLog.getAdminId());
            stmt.setString(2, adminActivityLog.getActivity());
            stmt.setString(3, adminActivityLog.getIpAddress());
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                adminActivityLog.setLogId(rs.getInt(1));
            }
            
            return findById(adminActivityLog.getLogId()).orElse(adminActivityLog);
        } catch (SQLException e) {
            logger.error("Error creating admin activity log", e);
            return adminActivityLog;
        }
    }

    @Override
    public void delete(Integer logId) {
        String sql = "DELETE FROM admin_activity_logs WHERE log_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, logId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting admin activity log", e);
        }
    }

    private AdminActivityLog extractAdminActivityLog(ResultSet rs) throws SQLException {
        AdminActivityLog log = new AdminActivityLog();
        log.setLogId(rs.getInt("log_id"));
        log.setAdminId(rs.getInt("admin_id"));
        log.setActivity(rs.getString("activity"));
        log.setIpAddress(rs.getString("ip_address"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            log.setCreatedAt(createdAt.toLocalDateTime());
        }

        return log;
    }
}
