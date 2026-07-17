package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.HomepageSectionProductDAO;
import com.nextcart.model.HomepageSectionProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomepageSectionProductDAOImpl implements HomepageSectionProductDAO {
    private static final Logger logger = LoggerFactory.getLogger(HomepageSectionProductDAOImpl.class);

    @Override
    public List<HomepageSectionProduct> findBySectionId(Integer sectionId) {
        List<HomepageSectionProduct> list = new ArrayList<>();
        String sql = "SELECT * FROM homepage_section_products WHERE section_id = ? ORDER BY display_order";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sectionId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HomepageSectionProduct sp = new HomepageSectionProduct();
                sp.setId(rs.getInt("id"));
                sp.setSectionId(rs.getInt("section_id"));
                sp.setProductId(rs.getInt("product_id"));
                sp.setDisplayOrder(rs.getInt("display_order"));
                list.add(sp);
            }
        } catch (SQLException e) {
            logger.error("Error finding products for section: {}", sectionId, e);
        }
        return list;
    }

    @Override
    public HomepageSectionProduct create(HomepageSectionProduct sp) {
        String sql = "INSERT INTO homepage_section_products (section_id, product_id, display_order) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, sp.getSectionId());
            stmt.setInt(2, sp.getProductId());
            stmt.setInt(3, sp.getDisplayOrder() != null ? sp.getDisplayOrder() : 0);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                sp.setId(rs.getInt(1));
            }
            return sp;
        } catch (SQLException e) {
            logger.error("Error adding product to section", e);
            return sp;
        }
    }

    @Override
    public void deleteBySectionId(Integer sectionId) {
        String sql = "DELETE FROM homepage_section_products WHERE section_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sectionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting products for section: {}", sectionId, e);
        }
    }
}
