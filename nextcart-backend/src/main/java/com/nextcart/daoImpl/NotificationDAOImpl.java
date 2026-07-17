package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.NotificationDAO;
import com.nextcart.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NotificationDAOImpl implements NotificationDAO {
    private static final Logger logger = LoggerFactory.getLogger(NotificationDAOImpl.class);

    @Override
    public Optional<Notification> findById(Integer id) {
        String sql = "SELECT * FROM notifications WHERE notification_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractNotification(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding notification by id: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Notification> findByUserId(Integer userId) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                notifications.add(extractNotification(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding notifications by user id: " + userId, e);
        }

        return notifications;
    }

    @Override
    public List<Notification> findByUserIdAndIsRead(Integer userId, Boolean isRead) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE user_id = ? AND is_read = ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setBoolean(2, isRead);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                notifications.add(extractNotification(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding notifications by user id and isRead: " + userId, e);
        }

        return notifications;
    }

    @Override
    public Notification create(Notification notification) {
        String sql = "INSERT INTO notifications (user_id, title, message, notification_type, is_read) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, notification.getUserId());
            stmt.setString(2, notification.getTitle());
            stmt.setString(3, notification.getMessage());
            stmt.setString(4, notification.getNotificationType());
            stmt.setBoolean(5, notification.getIsRead() != null ? notification.getIsRead() : false);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                notification.setNotificationId(rs.getInt(1));
                return findById(notification.getNotificationId()).orElse(notification);
            }

        } catch (SQLException e) {
            logger.error("Error creating notification", e);
        }

        return notification;
    }

    @Override
    public Notification markAsRead(Integer id) {
        String sql = "UPDATE notifications SET is_read = true WHERE notification_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            return findById(id).orElse(null);

        } catch (SQLException e) {
            logger.error("Error marking notification as read: " + id, e);
            return null;
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM notifications WHERE notification_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error deleting notification: " + id, e);
        }
    }

    private Notification extractNotification(ResultSet rs) throws SQLException {
        Notification notification = new Notification();
        notification.setNotificationId(rs.getInt("notification_id"));
        notification.setUserId(rs.getInt("user_id"));
        notification.setTitle(rs.getString("title"));
        notification.setMessage(rs.getString("message"));
        notification.setNotificationType(rs.getString("notification_type"));
        notification.setIsRead(rs.getBoolean("is_read"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            notification.setCreatedAt(createdAt.toLocalDateTime());
        }

        return notification;
    }
}
