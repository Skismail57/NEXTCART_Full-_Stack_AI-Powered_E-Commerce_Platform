package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.FlashSaleProductDAO;
import com.nextcart.model.FlashSaleProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlashSaleProductDAOImpl implements FlashSaleProductDAO {
    private static final Logger logger = LoggerFactory.getLogger(FlashSaleProductDAOImpl.class);

    @Override
    public List<FlashSaleProduct> findByFlashSaleId(Integer flashSaleId) {
        List<FlashSaleProduct> products = new ArrayList<>();
        String sql = "SELECT * FROM flash_sale_products WHERE flash_sale_id = ? ORDER BY display_order";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, flashSaleId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FlashSaleProduct fsp = new FlashSaleProduct();
                fsp.setId(rs.getInt("id"));
                fsp.setFlashSaleId(rs.getInt("flash_sale_id"));
                fsp.setProductId(rs.getInt("product_id"));
                fsp.setDisplayOrder(rs.getInt("display_order"));
                products.add(fsp);
            }
        } catch (SQLException e) {
            logger.error("Error finding flash sale products for id: " + flashSaleId, e);
        }
        return products;
    }

    @Override
    public FlashSaleProduct create(FlashSaleProduct flashSaleProduct) {
        String sql = "INSERT INTO flash_sale_products (flash_sale_id, product_id, display_order) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, flashSaleProduct.getFlashSaleId());
            stmt.setInt(2, flashSaleProduct.getProductId());
            stmt.setInt(3, flashSaleProduct.getDisplayOrder() != null ? flashSaleProduct.getDisplayOrder() : 0);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                flashSaleProduct.setId(rs.getInt(1));
            }
            return flashSaleProduct;
        } catch (SQLException e) {
            logger.error("Error creating flash sale product", e);
            return flashSaleProduct;
        }
    }

    @Override
    public void deleteByFlashSaleId(Integer flashSaleId) {
        String sql = "DELETE FROM flash_sale_products WHERE flash_sale_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, flashSaleId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting flash sale products for id: " + flashSaleId, e);
        }
    }
}
