package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.CategoryDAO;
import com.nextcart.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDAOImpl implements CategoryDAO {
    private static final Logger logger = LoggerFactory.getLogger(CategoryDAOImpl.class);

    @Override
    public Optional<Category> findById(Integer categoryId) {
        String sql = "SELECT * FROM categories WHERE category_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractCategory(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding category by id: " + categoryId, e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Category> findBySlug(String slug) {
        String sql = "SELECT * FROM categories WHERE slug = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, slug);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractCategory(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding category by slug: " + slug, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE status = 'ACTIVE' ORDER BY name";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                categories.add(extractCategory(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding all categories", e);
        }

        return categories;
    }

    @Override
    public Category create(Category category) {
        String sql = "INSERT INTO categories (name, slug, image, description, status) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getSlug());
            stmt.setString(3, category.getImage());
            stmt.setString(4, category.getDescription());
            stmt.setString(5, category.getStatus() != null ? category.getStatus() : "ACTIVE");
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                category.setCategoryId(rs.getInt(1));
            }
            
            return findById(category.getCategoryId()).orElse(category);
        } catch (SQLException e) {
            logger.error("Error creating category", e);
            return category;
        }
    }

    @Override
    public Category update(Category category) {
        String sql = "UPDATE categories SET name = ?, slug = ?, image = ?, description = ?, status = ? WHERE category_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getSlug());
            stmt.setString(3, category.getImage());
            stmt.setString(4, category.getDescription());
            stmt.setString(5, category.getStatus());
            stmt.setInt(6, category.getCategoryId());
            
            stmt.executeUpdate();
            return findById(category.getCategoryId()).orElse(category);
        } catch (SQLException e) {
            logger.error("Error updating category", e);
            return category;
        }
    }

    @Override
    public void delete(Integer categoryId) {
        String sql = "DELETE FROM categories WHERE category_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, categoryId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting category", e);
        }
    }

    private Category extractCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setCategoryId(rs.getInt("category_id"));
        category.setName(rs.getString("name"));
        category.setSlug(rs.getString("slug"));
        category.setImage(rs.getString("image"));
        category.setDescription(rs.getString("description"));
        category.setStatus(rs.getString("status"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            category.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            category.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return category;
    }
}
