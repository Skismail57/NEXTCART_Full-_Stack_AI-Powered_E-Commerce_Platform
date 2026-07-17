package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.SystemSettingDAO;
import com.nextcart.model.SystemSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SystemSettingDAOImpl implements SystemSettingDAO {
    private static final Logger logger = LoggerFactory.getLogger(SystemSettingDAOImpl.class);

    @Override
    public Optional<SystemSetting> findById(Integer settingId) {
        String sql = "SELECT * FROM system_settings WHERE setting_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, settingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractSystemSetting(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding system setting by id: " + settingId, e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<SystemSetting> findByKey(String settingKey) {
        String sql = "SELECT * FROM system_settings WHERE setting_key = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, settingKey);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractSystemSetting(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding system setting by key: " + settingKey, e);
        }

        return Optional.empty();
    }

    @Override
    public List<SystemSetting> findAll() {
        List<SystemSetting> systemSettings = new ArrayList<>();
        String sql = "SELECT * FROM system_settings ORDER BY setting_key";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                systemSettings.add(extractSystemSetting(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding all system settings", e);
        }

        return systemSettings;
    }

    @Override
    public SystemSetting create(SystemSetting systemSetting) {
        String sql = "INSERT INTO system_settings (setting_key, setting_value) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, systemSetting.getSettingKey());
            stmt.setString(2, systemSetting.getSettingValue());
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                systemSetting.setSettingId(rs.getInt(1));
            }
            
            return findById(systemSetting.getSettingId()).orElse(systemSetting);
        } catch (SQLException e) {
            logger.error("Error creating system setting", e);
            return systemSetting;
        }
    }

    @Override
    public SystemSetting update(SystemSetting systemSetting) {
        String sql = "UPDATE system_settings SET setting_key = ?, setting_value = ? WHERE setting_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, systemSetting.getSettingKey());
            stmt.setString(2, systemSetting.getSettingValue());
            stmt.setInt(3, systemSetting.getSettingId());
            
            stmt.executeUpdate();
            return findById(systemSetting.getSettingId()).orElse(systemSetting);
        } catch (SQLException e) {
            logger.error("Error updating system setting", e);
            return systemSetting;
        }
    }

    @Override
    public SystemSetting saveOrUpdate(SystemSetting systemSetting) {
        Optional<SystemSetting> existing = findByKey(systemSetting.getSettingKey());
        if (existing.isPresent()) {
            systemSetting.setSettingId(existing.get().getSettingId());
            return update(systemSetting);
        } else {
            return create(systemSetting);
        }
    }

    @Override
    public void delete(Integer settingId) {
        String sql = "DELETE FROM system_settings WHERE setting_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, settingId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting system setting", e);
        }
    }

    private SystemSetting extractSystemSetting(ResultSet rs) throws SQLException {
        SystemSetting systemSetting = new SystemSetting();
        systemSetting.setSettingId(rs.getInt("setting_id"));
        systemSetting.setSettingKey(rs.getString("setting_key"));
        systemSetting.setSettingValue(rs.getString("setting_value"));

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            systemSetting.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return systemSetting;
    }
}
