package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.ProductVariantDAO;
import com.nextcart.model.ProductVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductVariantDAOImpl implements ProductVariantDAO {
    private static final Logger logger = LoggerFactory.getLogger(ProductVariantDAOImpl.class);

    @Override
    public ProductVariant create(ProductVariant productVariant) {
        String sql = "INSERT INTO product_variants (product_id, color, size, ram, storage, price, stock, sku, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, productVariant.getProductId());
            stmt.setString(2, productVariant.getColor());
            stmt.setString(3, productVariant.getSize());
            stmt.setString(4, productVariant.getRam());
            stmt.setString(5, productVariant.getStorage());
            stmt.setBigDecimal(6, productVariant.getPrice());
            stmt.setInt(7, productVariant.getStock() != null ? productVariant.getStock() : 0);
            stmt.setString(8, productVariant.getSku());
            stmt.setString(9, productVariant.getStatus() != null ? productVariant.getStatus() : "ACTIVE");
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                productVariant.setVariantId(rs.getInt(1));
            }
            
        } catch (SQLException e) {
            logger.error("Error creating product variant", e);
        }
        
        return productVariant;
    }

    @Override
    public List<ProductVariant> findByProductId(Integer productId) {
        List<ProductVariant> variants = new ArrayList<>();
        String sql = "SELECT * FROM product_variants WHERE product_id = ? ORDER BY variant_id";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                variants.add(extractProductVariant(rs));
            }
            
        } catch (SQLException e) {
            logger.error("Error finding product variants by product id: " + productId, e);
        }
        
        return variants;
    }

    @Override
    public void deleteByProductId(Integer productId) {
        String sql = "DELETE FROM product_variants WHERE product_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, productId);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            logger.error("Error deleting product variants by product id: " + productId, e);
        }
    }

    private ProductVariant extractProductVariant(ResultSet rs) throws SQLException {
        ProductVariant variant = new ProductVariant();
        variant.setVariantId(rs.getInt("variant_id"));
        variant.setProductId(rs.getInt("product_id"));
        variant.setColor(rs.getString("color"));
        variant.setSize(rs.getString("size"));
        variant.setRam(rs.getString("ram"));
        variant.setStorage(rs.getString("storage"));
        variant.setPrice(rs.getBigDecimal("price"));
        variant.setStock(rs.getInt("stock"));
        variant.setSku(rs.getString("sku"));
        variant.setStatus(rs.getString("status"));
        
        return variant;
    }
}
