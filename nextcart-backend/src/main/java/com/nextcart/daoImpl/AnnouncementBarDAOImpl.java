package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.AnnouncementBarDAO;
import com.nextcart.model.AnnouncementBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnnouncementBarDAOImpl implements AnnouncementBarDAO {
    private static final Logger logger = LoggerFactory.getLogger(AnnouncementBarDAOImpl.class);

    @Override
    public Optional<AnnouncementBar> findById(Integer announcementId) {
        String sql = "SELECT * FROM announcement_bar WHERE announcement_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, announcementId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractAnnouncementBar(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding announcement by id: {}", announcementId, e);
        }
        return Optional.empty();
    }

    @Override
    public List<AnnouncementBar> findAll() {
        List<AnnouncementBar> announcements = new ArrayList<>();
        String sql = "SELECT * FROM announcement_bar ORDER BY display_order";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                announcements.add(extractAnnouncementBar(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all announcements", e);
        }
        return announcements;
    }

    @Override
    public List<AnnouncementBar> findActive() {
        List<AnnouncementBar> announcements = new ArrayList<>();
        String sql = "SELECT * FROM announcement_bar WHERE status = 'ACTIVE' ORDER BY display_order";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                announcements.add(extractAnnouncementBar(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding active announcements", e);
        }
        return announcements;
    }

    @Override
    public AnnouncementBar create(AnnouncementBar announcement) {
        String sql = "INSERT INTO announcement_bar (text, link, background_color, text_color, status, display_order) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, announcement.getText());
            stmt.setString(2, announcement.getLink());
            stmt.setString(3, announcement.getBackgroundColor());
            stmt.setString(4, announcement.getTextColor());
            stmt.setString(5, announcement.getStatus() != null ? announcement.getStatus() : "ACTIVE");
            stmt.setInt(6, announcement.getDisplayOrder() != null ? announcement.getDisplayOrder() : 0);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                announcement.setAnnouncementId(rs.getInt(1));
            }
            return findById(announcement.getAnnouncementId()).orElse(announcement);
        } catch (SQLException e) {
            logger.error("Error creating announcement", e);
            return announcement;
        }
    }

    @Override
    public AnnouncementBar update(AnnouncementBar announcement) {
        String sql = "UPDATE announcement_bar SET text = ?, link = ?, background_color = ?, text_color = ?, " +
                     "status = ?, display_order = ? WHERE announcement_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, announcement.getText());
            stmt.setString(2, announcement.getLink());
            stmt.setString(3, announcement.getBackgroundColor());
            stmt.setString(4, announcement.getTextColor());
            stmt.setString(5, announcement.getStatus());
            stmt.setInt(6, announcement.getDisplayOrder() != null ? announcement.getDisplayOrder() : 0);
            stmt.setInt(7, announcement.getAnnouncementId());
            stmt.executeUpdate();
            return findById(announcement.getAnnouncementId()).orElse(announcement);
        } catch (SQLException e) {
            logger.error("Error updating announcement", e);
            return announcement;
        }
    }

    @Override
    public void delete(Integer announcementId) {
        String sql = "DELETE FROM announcement_bar WHERE announcement_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, announcementId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting announcement: {}", announcementId, e);
        }
    }

    private AnnouncementBar extractAnnouncementBar(ResultSet rs) throws SQLException {
        AnnouncementBar announcement = new AnnouncementBar();
        announcement.setAnnouncementId(rs.getInt("announcement_id"));
        announcement.setText(rs.getString("text"));
        announcement.setLink(rs.getString("link"));
        announcement.setBackgroundColor(rs.getString("background_color"));
        announcement.setTextColor(rs.getString("text_color"));
        announcement.setStatus(rs.getString("status"));
        announcement.setDisplayOrder(rs.getInt("display_order"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            announcement.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            announcement.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return announcement;
    }
}
