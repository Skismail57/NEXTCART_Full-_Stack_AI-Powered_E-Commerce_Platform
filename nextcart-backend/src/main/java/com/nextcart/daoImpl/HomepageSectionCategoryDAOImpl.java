package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.HomepageSectionCategoryDAO;
import com.nextcart.model.HomepageSectionCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomepageSectionCategoryDAOImpl implements HomepageSectionCategoryDAO {
    private static final Logger logger = LoggerFactory.getLogger(HomepageSectionCategoryDAOImpl.class);

    @Override
    public List<HomepageSectionCategory> findBySectionId(Integer sectionId) {
        List<HomepageSectionCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM homepage_section_categories WHERE section_id = ? ORDER BY display_order";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sectionId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HomepageSectionCategory sc = new HomepageSectionCategory();
                sc.setId(rs.getInt("id"));
                sc.setSectionId(rs.getInt("section_id"));
                sc.setCategoryId(rs.getInt("category_id"));
                sc.setDisplayOrder(rs.getInt("display_order"));
                list.add(sc);
            }
        } catch (SQLException e) {
            logger.error("Error finding categories for section: {}", sectionId, e);
        }
        return list;
    }

    @Override
    public HomepageSectionCategory create(HomepageSectionCategory sc) {
        String sql = "INSERT INTO homepage_section_categories (section_id, category_id, display_order) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, sc.getSectionId());
            stmt.setInt(2, sc.getCategoryId());
            stmt.setInt(3, sc.getDisplayOrder() != null ? sc.getDisplayOrder() : 0);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                sc.setId(rs.getInt(1));
            }
            return sc;
        } catch (SQLException e) {
            logger.error("Error adding category to section", e);
            return sc;
        }
    }

    @Override
    public void deleteBySectionId(Integer sectionId) {
        String sql = "DELETE FROM homepage_section_categories WHERE section_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sectionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting categories for section: {}", sectionId, e);
        }
    }
}
