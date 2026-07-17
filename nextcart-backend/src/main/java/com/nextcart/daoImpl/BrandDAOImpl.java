package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.BrandDAO;
import com.nextcart.model.Brand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BrandDAOImpl implements BrandDAO {
    private static final Logger logger = LoggerFactory.getLogger(BrandDAOImpl.class);

    @Override
    public Optional<Brand> findById(Integer brandId) {
        String sql = "SELECT * FROM brands WHERE brand_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, brandId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractBrand(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding brand by id: " + brandId, e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Brand> findBySlug(String slug) {
        String sql = "SELECT * FROM brands WHERE brand_name = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, slug);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractBrand(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding brand by slug: " + slug, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Brand> findAll() {
        List<Brand> brands = new ArrayList<>();
        String sql = "SELECT * FROM brands WHERE status = 'ACTIVE' ORDER BY brand_name";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                brands.add(extractBrand(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding all brands", e);
        }

        return brands;
    }

    @Override
    public Brand create(Brand brand) {
        String sql = "INSERT INTO brands (brand_name, brand_logo, description, status) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, brand.getBrandName());
            stmt.setString(2, brand.getBrandLogo());
            stmt.setString(3, brand.getDescription());
            stmt.setString(4, brand.getStatus() != null ? brand.getStatus() : "ACTIVE");
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                brand.setBrandId(rs.getInt(1));
            }
            
            return findById(brand.getBrandId()).orElse(brand);
        } catch (SQLException e) {
            logger.error("Error creating brand", e);
            return brand;
        }
    }

    @Override
    public Brand update(Brand brand) {
        String sql = "UPDATE brands SET brand_name = ?, brand_logo = ?, description = ?, status = ? WHERE brand_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, brand.getBrandName());
            stmt.setString(2, brand.getBrandLogo());
            stmt.setString(3, brand.getDescription());
            stmt.setString(4, brand.getStatus());
            stmt.setInt(5, brand.getBrandId());
            
            stmt.executeUpdate();
            return findById(brand.getBrandId()).orElse(brand);
        } catch (SQLException e) {
            logger.error("Error updating brand", e);
            return brand;
        }
    }

    @Override
    public void delete(Integer brandId) {
        String sql = "DELETE FROM brands WHERE brand_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, brandId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting brand", e);
        }
    }

    private Brand extractBrand(ResultSet rs) throws SQLException {
        Brand brand = new Brand();
        brand.setBrandId(rs.getInt("brand_id"));
        brand.setBrandName(rs.getString("brand_name"));
        brand.setBrandLogo(rs.getString("brand_logo"));
        brand.setDescription(rs.getString("description"));
        brand.setStatus(rs.getString("status"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            brand.setCreatedAt(createdAt.toLocalDateTime());
        }

        return brand;
    }
}
