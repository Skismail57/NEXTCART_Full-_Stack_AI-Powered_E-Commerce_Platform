package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.HeroBannerDAO;
import com.nextcart.model.HeroBanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HeroBannerDAOImpl implements HeroBannerDAO {
    private static final Logger logger = LoggerFactory.getLogger(HeroBannerDAOImpl.class);

    @Override
    public Optional<HeroBanner> findById(Integer heroId) {
        String sql = "SELECT * FROM hero_banners WHERE hero_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, heroId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractHeroBanner(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding hero banner by id: {}", heroId, e);
        }
        return Optional.empty();
    }

    @Override
    public List<HeroBanner> findAll() {
        List<HeroBanner> heroBanners = new ArrayList<>();
        String sql = "SELECT * FROM hero_banners ORDER BY display_order, created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                heroBanners.add(extractHeroBanner(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all hero banners", e);
        }
        return heroBanners;
    }

    @Override
    public List<HeroBanner> findActive() {
        List<HeroBanner> heroBanners = new ArrayList<>();
        String sql = "SELECT * FROM hero_banners WHERE status = 'ACTIVE' ORDER BY display_order, created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                heroBanners.add(extractHeroBanner(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding active hero banners", e);
        }
        return heroBanners;
    }

    @Override
    public HeroBanner create(HeroBanner heroBanner) {
        String sql = "INSERT INTO hero_banners (title, subtitle, image_url, button_text, button_link, display_order, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, heroBanner.getTitle());
            stmt.setString(2, heroBanner.getSubtitle());
            stmt.setString(3, heroBanner.getImageUrl());
            stmt.setString(4, heroBanner.getButtonText());
            stmt.setString(5, heroBanner.getButtonLink());
            stmt.setObject(6, heroBanner.getDisplayOrder(), Types.INTEGER);
            stmt.setString(7, heroBanner.getStatus() != null ? heroBanner.getStatus() : "ACTIVE");
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                heroBanner.setHeroId(rs.getInt(1));
                return findById(heroBanner.getHeroId()).orElse(heroBanner);
            }
        } catch (SQLException e) {
            logger.error("Error creating hero banner", e);
        }
        return heroBanner;
    }

    @Override
    public HeroBanner update(HeroBanner heroBanner) {
        String sql = "UPDATE hero_banners SET title = ?, subtitle = ?, image_url = ?, button_text = ?, button_link = ?, display_order = ?, status = ? WHERE hero_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, heroBanner.getTitle());
            stmt.setString(2, heroBanner.getSubtitle());
            stmt.setString(3, heroBanner.getImageUrl());
            stmt.setString(4, heroBanner.getButtonText());
            stmt.setString(5, heroBanner.getButtonLink());
            stmt.setObject(6, heroBanner.getDisplayOrder(), Types.INTEGER);
            stmt.setString(7, heroBanner.getStatus());
            stmt.setInt(8, heroBanner.getHeroId());
            stmt.executeUpdate();
            return findById(heroBanner.getHeroId()).orElse(heroBanner);
        } catch (SQLException e) {
            logger.error("Error updating hero banner: {}", heroBanner.getHeroId(), e);
            return heroBanner;
        }
    }

    @Override
    public void delete(Integer heroId) {
        String sql = "DELETE FROM hero_banners WHERE hero_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, heroId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting hero banner: {}", heroId, e);
        }
    }

    private HeroBanner extractHeroBanner(ResultSet rs) throws SQLException {
        HeroBanner heroBanner = new HeroBanner();
        heroBanner.setHeroId(rs.getInt("hero_id"));
        heroBanner.setTitle(rs.getString("title"));
        heroBanner.setSubtitle(rs.getString("subtitle"));
        heroBanner.setImageUrl(rs.getString("image_url"));
        heroBanner.setButtonText(rs.getString("button_text"));
        heroBanner.setButtonLink(rs.getString("button_link"));
        heroBanner.setDisplayOrder((Integer) rs.getObject("display_order"));
        heroBanner.setStatus(rs.getString("status"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            // HeroBanner model doesn't have createdAt, so we'll skip
        }
        return heroBanner;
    }
}
