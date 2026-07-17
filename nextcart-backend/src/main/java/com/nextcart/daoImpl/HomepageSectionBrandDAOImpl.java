package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.HomepageSectionBrandDAO;
import com.nextcart.model.HomepageSectionBrand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomepageSectionBrandDAOImpl implements HomepageSectionBrandDAO {
    private static final Logger logger = LoggerFactory.getLogger(HomepageSectionBrandDAOImpl.class);

    @Override
    public List<HomepageSectionBrand> findBySectionId(Integer sectionId) {
        List<HomepageSectionBrand> list = new ArrayList<>();
        String sql = "SELECT * FROM homepage_section_brands WHERE section_id = ? ORDER BY display_order";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sectionId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HomepageSectionBrand sb = new HomepageSectionBrand();
                sb.setId(rs.getInt("id"));
                sb.setSectionId(rs.getInt("section_id"));
                sb.setBrandId(rs.getInt("brand_id"));
                sb.setDisplayOrder(rs.getInt("display_order"));
                list.add(sb);
            }
        } catch (SQLException e) {
            logger.error("Error finding brands for section: {}", sectionId, e);
        }
        return list;
    }

    @Override
    public HomepageSectionBrand create(HomepageSectionBrand sb) {
        String sql = "INSERT INTO homepage_section_brands (section_id, brand_id, display_order) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, sb.getSectionId());
            stmt.setInt(2, sb.getBrandId());
            stmt.setInt(3, sb.getDisplayOrder() != null ? sb.getDisplayOrder() : 0);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                sb.setId(rs.getInt(1));
            }
            return sb;
        } catch (SQLException e) {
            logger.error("Error adding brand to section", e);
            return sb;
        }
    }

    @Override
    public void deleteBySectionId(Integer sectionId) {
        String sql = "DELETE FROM homepage_section_brands WHERE section_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sectionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting brands for section: {}", sectionId, e);
        }
    }
}
