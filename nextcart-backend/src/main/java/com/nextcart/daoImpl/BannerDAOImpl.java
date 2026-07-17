package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.BannerDAO;
import com.nextcart.model.Banner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BannerDAOImpl implements BannerDAO {
    private static final Logger logger = LoggerFactory.getLogger(BannerDAOImpl.class);

    @Override
    public Optional<Banner> findById(Integer bannerId) {
        String sql = "SELECT * FROM banners WHERE banner_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bannerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractBanner(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding banner by id: {}", bannerId, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Banner> findAll() {
        List<Banner> banners = new ArrayList<>();
        String sql = "SELECT * FROM banners ORDER BY display_order, created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                banners.add(extractBanner(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all banners", e);
        }
        return banners;
    }

    @Override
    public List<Banner> findActive() {
        List<Banner> banners = new ArrayList<>();
        String sql = "SELECT * FROM banners WHERE status = 'ACTIVE' ORDER BY display_order, created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                banners.add(extractBanner(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding active banners", e);
        }
        return banners;
    }

    @Override
    public Banner create(Banner banner) {
        String sql = "INSERT INTO banners (title, image_url, redirect_url, display_order, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, banner.getTitle());
            stmt.setString(2, banner.getImageUrl());
            stmt.setString(3, banner.getRedirectUrl());
            stmt.setObject(4, banner.getDisplayOrder(), Types.INTEGER);
            stmt.setString(5, banner.getStatus() != null ? banner.getStatus() : "ACTIVE");
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                banner.setBannerId(rs.getInt(1));
                return findById(banner.getBannerId()).orElse(banner);
            }
        } catch (SQLException e) {
            logger.error("Error creating banner", e);
        }
        return banner;
    }

    @Override
    public Banner update(Banner banner) {
        String sql = "UPDATE banners SET title = ?, image_url = ?, redirect_url = ?, display_order = ?, status = ? WHERE banner_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, banner.getTitle());
            stmt.setString(2, banner.getImageUrl());
            stmt.setString(3, banner.getRedirectUrl());
            stmt.setObject(4, banner.getDisplayOrder(), Types.INTEGER);
            stmt.setString(5, banner.getStatus());
            stmt.setInt(6, banner.getBannerId());
            stmt.executeUpdate();
            return findById(banner.getBannerId()).orElse(banner);
        } catch (SQLException e) {
            logger.error("Error updating banner: {}", banner.getBannerId(), e);
            return banner;
        }
    }

    @Override
    public void delete(Integer bannerId) {
        String sql = "DELETE FROM banners WHERE banner_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bannerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting banner: {}", bannerId, e);
        }
    }

    private Banner extractBanner(ResultSet rs) throws SQLException {
        Banner banner = new Banner();
        banner.setBannerId(rs.getInt("banner_id"));
        banner.setTitle(rs.getString("title"));
        banner.setImageUrl(rs.getString("image_url"));
        banner.setRedirectUrl(rs.getString("redirect_url"));
        banner.setDisplayOrder((Integer) rs.getObject("display_order"));
        banner.setStatus(rs.getString("status"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            banner.setCreatedAt(createdAt.toLocalDateTime());
        }
        return banner;
    }
}
