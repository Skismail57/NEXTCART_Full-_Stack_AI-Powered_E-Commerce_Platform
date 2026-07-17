package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.HomepageBannerDAO;
import com.nextcart.model.HomepageBanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HomepageBannerDAOImpl implements HomepageBannerDAO {
    private static final Logger logger = LoggerFactory.getLogger(HomepageBannerDAOImpl.class);

    @Override
    public Optional<HomepageBanner> findById(Integer bannerId) {
        String sql = "SELECT * FROM homepage_banners WHERE banner_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bannerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractHomepageBanner(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding homepage banner by id: {}", bannerId, e);
        }
        return Optional.empty();
    }

    @Override
    public List<HomepageBanner> findAll() {
        List<HomepageBanner> banners = new ArrayList<>();
        String sql = "SELECT * FROM homepage_banners ORDER BY position, display_order";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                banners.add(extractHomepageBanner(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all homepage banners", e);
        }
        return banners;
    }

    @Override
    public List<HomepageBanner> findByPosition(String position) {
        List<HomepageBanner> banners = new ArrayList<>();
        String sql = "SELECT * FROM homepage_banners WHERE position = ? ORDER BY display_order";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, position);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                banners.add(extractHomepageBanner(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding homepage banners by position: {}", position, e);
        }
        return banners;
    }

    @Override
    public List<HomepageBanner> findActive() {
        List<HomepageBanner> banners = new ArrayList<>();
        String sql = "SELECT * FROM homepage_banners WHERE status = 'ACTIVE' " +
                     "AND (start_date IS NULL OR start_date <= CURRENT_TIMESTAMP) " +
                     "AND (end_date IS NULL OR end_date >= CURRENT_TIMESTAMP) " +
                     "ORDER BY position, display_order";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                banners.add(extractHomepageBanner(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding active homepage banners", e);
        }
        return banners;
    }

    @Override
    public HomepageBanner create(HomepageBanner banner) {
        String sql = "INSERT INTO homepage_banners (position, title, image_url, redirect_url, display_order, status, start_date, end_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, banner.getPosition());
            stmt.setString(2, banner.getTitle());
            stmt.setString(3, banner.getImageUrl());
            stmt.setString(4, banner.getRedirectUrl());
            stmt.setInt(5, banner.getDisplayOrder() != null ? banner.getDisplayOrder() : 0);
            stmt.setString(6, banner.getStatus() != null ? banner.getStatus() : "ACTIVE");
            stmt.setTimestamp(7, banner.getStartDate() != null ? Timestamp.valueOf(banner.getStartDate()) : null);
            stmt.setTimestamp(8, banner.getEndDate() != null ? Timestamp.valueOf(banner.getEndDate()) : null);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                banner.setBannerId(rs.getInt(1));
            }
            return findById(banner.getBannerId()).orElse(banner);
        } catch (SQLException e) {
            logger.error("Error creating homepage banner", e);
            return banner;
        }
    }

    @Override
    public HomepageBanner update(HomepageBanner banner) {
        String sql = "UPDATE homepage_banners SET position = ?, title = ?, image_url = ?, redirect_url = ?, " +
                     "display_order = ?, status = ?, start_date = ?, end_date = ? WHERE banner_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, banner.getPosition());
            stmt.setString(2, banner.getTitle());
            stmt.setString(3, banner.getImageUrl());
            stmt.setString(4, banner.getRedirectUrl());
            stmt.setInt(5, banner.getDisplayOrder() != null ? banner.getDisplayOrder() : 0);
            stmt.setString(6, banner.getStatus());
            stmt.setTimestamp(7, banner.getStartDate() != null ? Timestamp.valueOf(banner.getStartDate()) : null);
            stmt.setTimestamp(8, banner.getEndDate() != null ? Timestamp.valueOf(banner.getEndDate()) : null);
            stmt.setInt(9, banner.getBannerId());
            stmt.executeUpdate();
            return findById(banner.getBannerId()).orElse(banner);
        } catch (SQLException e) {
            logger.error("Error updating homepage banner", e);
            return banner;
        }
    }

    @Override
    public void delete(Integer bannerId) {
        String sql = "DELETE FROM homepage_banners WHERE banner_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bannerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting homepage banner: {}", bannerId, e);
        }
    }

    private HomepageBanner extractHomepageBanner(ResultSet rs) throws SQLException {
        HomepageBanner banner = new HomepageBanner();
        banner.setBannerId(rs.getInt("banner_id"));
        banner.setPosition(rs.getString("position"));
        banner.setTitle(rs.getString("title"));
        banner.setImageUrl(rs.getString("image_url"));
        banner.setRedirectUrl(rs.getString("redirect_url"));
        banner.setDisplayOrder(rs.getInt("display_order"));
        banner.setStatus(rs.getString("status"));

        Timestamp startDate = rs.getTimestamp("start_date");
        if (startDate != null) {
            banner.setStartDate(startDate.toLocalDateTime());
        }
        Timestamp endDate = rs.getTimestamp("end_date");
        if (endDate != null) {
            banner.setEndDate(endDate.toLocalDateTime());
        }
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            banner.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            banner.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return banner;
    }
}
