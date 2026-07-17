package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.SubCategoryDAO;
import com.nextcart.model.SubCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubCategoryDAOImpl implements SubCategoryDAO {
    private static final Logger logger = LoggerFactory.getLogger(SubCategoryDAOImpl.class);

    @Override
    public Optional<SubCategory> findById(Integer subCategoryId) {
        String sql = "SELECT * FROM sub_categories WHERE sub_category_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, subCategoryId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractSubCategory(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding subcategory by id: {}", subCategoryId, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<SubCategory> findBySlug(String slug) {
        String sql = "SELECT * FROM sub_categories WHERE slug = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, slug);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractSubCategory(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding subcategory by slug: {}", slug, e);
        }
        return Optional.empty();
    }

    @Override
    public List<SubCategory> findAll() {
        List<SubCategory> subCategories = new ArrayList<>();
        String sql = "SELECT * FROM sub_categories WHERE status = 'ACTIVE' ORDER BY name";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                subCategories.add(extractSubCategory(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all subcategories", e);
        }
        return subCategories;
    }

    @Override
    public List<SubCategory> findByCategoryId(Integer categoryId) {
        List<SubCategory> subCategories = new ArrayList<>();
        String sql = "SELECT * FROM sub_categories WHERE category_id = ? AND status = 'ACTIVE' ORDER BY name";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                subCategories.add(extractSubCategory(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding subcategories by category id: {}", categoryId, e);
        }
        return subCategories;
    }

    @Override
    public SubCategory create(SubCategory subCategory) {
        String sql = "INSERT INTO sub_categories (category_id, name, slug, image, description, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, subCategory.getCategoryId());
            stmt.setString(2, subCategory.getName());
            stmt.setString(3, subCategory.getSlug());
            stmt.setString(4, subCategory.getImage());
            stmt.setString(5, subCategory.getDescription());
            stmt.setString(6, subCategory.getStatus() != null ? subCategory.getStatus() : "ACTIVE");
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                subCategory.setSubCategoryId(rs.getInt(1));
                return findById(subCategory.getSubCategoryId()).orElse(subCategory);
            }
        } catch (SQLException e) {
            logger.error("Error creating subcategory", e);
        }
        return subCategory;
    }

    @Override
    public SubCategory update(SubCategory subCategory) {
        String sql = "UPDATE sub_categories SET category_id = ?, name = ?, slug = ?, image = ?, description = ?, status = ? " +
                     "WHERE sub_category_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, subCategory.getCategoryId());
            stmt.setString(2, subCategory.getName());
            stmt.setString(3, subCategory.getSlug());
            stmt.setString(4, subCategory.getImage());
            stmt.setString(5, subCategory.getDescription());
            stmt.setString(6, subCategory.getStatus());
            stmt.setInt(7, subCategory.getSubCategoryId());
            stmt.executeUpdate();
            return findById(subCategory.getSubCategoryId()).orElse(subCategory);
        } catch (SQLException e) {
            logger.error("Error updating subcategory: {}", subCategory.getSubCategoryId(), e);
            return subCategory;
        }
    }

    @Override
    public boolean delete(Integer subCategoryId) {
        String sql = "DELETE FROM sub_categories WHERE sub_category_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, subCategoryId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting subcategory: {}", subCategoryId, e);
            return false;
        }
    }

    private SubCategory extractSubCategory(ResultSet rs) throws SQLException {
        SubCategory subCategory = new SubCategory();
        subCategory.setSubCategoryId(rs.getInt("sub_category_id"));
        subCategory.setCategoryId(rs.getInt("category_id"));
        subCategory.setName(rs.getString("name"));
        subCategory.setSlug(rs.getString("slug"));
        subCategory.setImage(rs.getString("image"));
        subCategory.setDescription(rs.getString("description"));
        subCategory.setStatus(rs.getString("status"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            subCategory.setCreatedAt(createdAt.toLocalDateTime());
        }
        return subCategory;
    }
}
