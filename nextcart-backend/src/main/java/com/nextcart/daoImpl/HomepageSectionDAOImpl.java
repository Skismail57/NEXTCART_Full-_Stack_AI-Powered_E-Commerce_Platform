package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.HomepageSectionDAO;
import com.nextcart.model.HomepageSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HomepageSectionDAOImpl implements HomepageSectionDAO {
    private static final Logger logger = LoggerFactory.getLogger(HomepageSectionDAOImpl.class);

    @Override
    public Optional<HomepageSection> findById(Integer sectionId) {
        String sql = "SELECT * FROM homepage_sections WHERE section_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sectionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractHomepageSection(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding homepage section by id: {}", sectionId, e);
        }
        return Optional.empty();
    }

    @Override
    public List<HomepageSection> findAll() {
        List<HomepageSection> sections = new ArrayList<>();
        String sql = "SELECT * FROM homepage_sections ORDER BY display_order";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                sections.add(extractHomepageSection(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all homepage sections", e);
        }
        return sections;
    }

    @Override
    public List<HomepageSection> findActive() {
        List<HomepageSection> sections = new ArrayList<>();
        String sql = "SELECT * FROM homepage_sections WHERE status = 'ACTIVE' ORDER BY display_order";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                sections.add(extractHomepageSection(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding active homepage sections", e);
        }
        return sections;
    }

    @Override
    public HomepageSection create(HomepageSection section) {
        String sql = "INSERT INTO homepage_sections (section_name, section_type, title, subtitle, layout, display_order, status, config) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, section.getSectionName());
            stmt.setString(2, section.getSectionType());
            stmt.setString(3, section.getTitle());
            stmt.setString(4, section.getSubtitle());
            stmt.setString(5, section.getLayout());
            stmt.setInt(6, section.getDisplayOrder() != null ? section.getDisplayOrder() : 0);
            stmt.setString(7, section.getStatus() != null ? section.getStatus() : "ACTIVE");
            stmt.setString(8, section.getConfig());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                section.setSectionId(rs.getInt(1));
            }
            return findById(section.getSectionId()).orElse(section);
        } catch (SQLException e) {
            logger.error("Error creating homepage section", e);
            return section;
        }
    }

    @Override
    public HomepageSection update(HomepageSection section) {
        String sql = "UPDATE homepage_sections SET section_name = ?, section_type = ?, title = ?, subtitle = ?, " +
                     "layout = ?, display_order = ?, status = ?, config = ? WHERE section_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, section.getSectionName());
            stmt.setString(2, section.getSectionType());
            stmt.setString(3, section.getTitle());
            stmt.setString(4, section.getSubtitle());
            stmt.setString(5, section.getLayout());
            stmt.setInt(6, section.getDisplayOrder() != null ? section.getDisplayOrder() : 0);
            stmt.setString(7, section.getStatus());
            stmt.setString(8, section.getConfig());
            stmt.setInt(9, section.getSectionId());
            stmt.executeUpdate();
            return findById(section.getSectionId()).orElse(section);
        } catch (SQLException e) {
            logger.error("Error updating homepage section", e);
            return section;
        }
    }

    @Override
    public void delete(Integer sectionId) {
        String sql = "DELETE FROM homepage_sections WHERE section_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sectionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting homepage section: {}", sectionId, e);
        }
    }

    private HomepageSection extractHomepageSection(ResultSet rs) throws SQLException {
        HomepageSection section = new HomepageSection();
        section.setSectionId(rs.getInt("section_id"));
        section.setSectionName(rs.getString("section_name"));
        section.setSectionType(rs.getString("section_type"));
        section.setTitle(rs.getString("title"));
        section.setSubtitle(rs.getString("subtitle"));
        section.setLayout(rs.getString("layout"));
        section.setDisplayOrder(rs.getInt("display_order"));
        section.setStatus(rs.getString("status"));
        section.setConfig(rs.getString("config"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            section.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            section.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return section;
    }
}
