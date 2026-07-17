package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.PopupDAO;
import com.nextcart.model.Popup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PopupDAOImpl implements PopupDAO {
    private static final Logger logger = LoggerFactory.getLogger(PopupDAOImpl.class);

    @Override
    public Optional<Popup> findById(Integer popupId) {
        String sql = "SELECT * FROM popups WHERE popup_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, popupId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractPopup(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding popup by id: " + popupId, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Popup> findAll() {
        List<Popup> popups = new ArrayList<>();
        String sql = "SELECT * FROM popups ORDER BY display_order";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                popups.add(extractPopup(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all popups", e);
        }
        return popups;
    }

    @Override
    public List<Popup> findActive() {
        List<Popup> popups = new ArrayList<>();
        String sql = "SELECT * FROM popups WHERE status = 'ACTIVE' " +
                     "AND (start_time IS NULL OR start_time <= CURRENT_TIMESTAMP) " +
                     "AND (end_time IS NULL OR end_time >= CURRENT_TIMESTAMP) " +
                     "ORDER BY display_order";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                popups.add(extractPopup(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding active popups", e);
        }
        return popups;
    }

    @Override
    public Popup create(Popup popup) {
        String sql = "INSERT INTO popups (name, title, content, image, button_text, button_link, status, start_time, end_time, display_order) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, popup.getName());
            stmt.setString(2, popup.getTitle());
            stmt.setString(3, popup.getContent());
            stmt.setString(4, popup.getImage());
            stmt.setString(5, popup.getButtonText());
            stmt.setString(6, popup.getButtonLink());
            stmt.setString(7, popup.getStatus() != null ? popup.getStatus() : "INACTIVE");
            stmt.setTimestamp(8, popup.getStartTime() != null ? Timestamp.valueOf(popup.getStartTime()) : null);
            stmt.setTimestamp(9, popup.getEndTime() != null ? Timestamp.valueOf(popup.getEndTime()) : null);
            stmt.setInt(10, popup.getDisplayOrder() != null ? popup.getDisplayOrder() : 0);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                popup.setPopupId(rs.getInt(1));
            }
            return findById(popup.getPopupId()).orElse(popup);
        } catch (SQLException e) {
            logger.error("Error creating popup", e);
            return popup;
        }
    }

    @Override
    public Popup update(Popup popup) {
        String sql = "UPDATE popups SET name = ?, title = ?, content = ?, image = ?, button_text = ?, button_link = ?, status = ?, start_time = ?, end_time = ?, display_order = ? WHERE popup_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, popup.getName());
            stmt.setString(2, popup.getTitle());
            stmt.setString(3, popup.getContent());
            stmt.setString(4, popup.getImage());
            stmt.setString(5, popup.getButtonText());
            stmt.setString(6, popup.getButtonLink());
            stmt.setString(7, popup.getStatus());
            stmt.setTimestamp(8, popup.getStartTime() != null ? Timestamp.valueOf(popup.getStartTime()) : null);
            stmt.setTimestamp(9, popup.getEndTime() != null ? Timestamp.valueOf(popup.getEndTime()) : null);
            stmt.setInt(10, popup.getDisplayOrder());
            stmt.setInt(11, popup.getPopupId());
            stmt.executeUpdate();
            return findById(popup.getPopupId()).orElse(popup);
        } catch (SQLException e) {
            logger.error("Error updating popup", e);
            return popup;
        }
    }

    @Override
    public void delete(Integer popupId) {
        String sql = "DELETE FROM popups WHERE popup_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, popupId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting popup: " + popupId, e);
        }
    }

    private Popup extractPopup(ResultSet rs) throws SQLException {
        Popup popup = new Popup();
        popup.setPopupId(rs.getInt("popup_id"));
        popup.setName(rs.getString("name"));
        popup.setTitle(rs.getString("title"));
        popup.setContent(rs.getString("content"));
        popup.setImage(rs.getString("image"));
        popup.setButtonText(rs.getString("button_text"));
        popup.setButtonLink(rs.getString("button_link"));
        popup.setStatus(rs.getString("status"));
        Timestamp startTime = rs.getTimestamp("start_time");
        if (startTime != null) {
            popup.setStartTime(startTime.toLocalDateTime());
        }
        Timestamp endTime = rs.getTimestamp("end_time");
        if (endTime != null) {
            popup.setEndTime(endTime.toLocalDateTime());
        }
        popup.setDisplayOrder(rs.getInt("display_order"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            popup.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            popup.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return popup;
    }
}
