package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.ProductImageDAO;
import com.nextcart.model.ProductImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductImageDAOImpl implements ProductImageDAO {
    private static final Logger logger = LoggerFactory.getLogger(ProductImageDAOImpl.class);

    @Override
    public ProductImage create(ProductImage productImage) {
        String sql = "INSERT INTO product_images (product_id, image_url, display_order, is_primary) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, productImage.getProductId());
            stmt.setString(2, productImage.getImageUrl());
            stmt.setInt(3, productImage.getDisplayOrder() != null ? productImage.getDisplayOrder() : 0);
            stmt.setBoolean(4, productImage.getIsPrimary() != null ? productImage.getIsPrimary() : false);
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                productImage.setImageId(rs.getInt(1));
            }
            
        } catch (SQLException e) {
            logger.error("Error creating product image", e);
        }
        
        return productImage;
    }

    @Override
    public List<ProductImage> findByProductId(Integer productId) {
        List<ProductImage> images = new ArrayList<>();
        String sql = "SELECT * FROM product_images WHERE product_id = ? ORDER BY display_order, image_id";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                images.add(extractProductImage(rs));
            }
            
        } catch (SQLException e) {
            logger.error("Error finding product images by product id: " + productId, e);
        }
        
        return images;
    }

    @Override
    public void deleteByProductId(Integer productId) {
        String sql = "DELETE FROM product_images WHERE product_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, productId);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            logger.error("Error deleting product images by product id: " + productId, e);
        }
    }

    private ProductImage extractProductImage(ResultSet rs) throws SQLException {
        ProductImage image = new ProductImage();
        image.setImageId(rs.getInt("image_id"));
        image.setProductId(rs.getInt("product_id"));
        image.setImageUrl(rs.getString("image_url"));
        image.setDisplayOrder(rs.getInt("display_order"));
        image.setIsPrimary(rs.getBoolean("is_primary"));
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            image.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        return image;
    }
}
