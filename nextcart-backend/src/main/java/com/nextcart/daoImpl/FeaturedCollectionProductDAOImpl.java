package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.FeaturedCollectionProductDAO;
import com.nextcart.model.FeaturedCollectionProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeaturedCollectionProductDAOImpl implements FeaturedCollectionProductDAO {
    private static final Logger logger = LoggerFactory.getLogger(FeaturedCollectionProductDAOImpl.class);

    @Override
    public List<FeaturedCollectionProduct> findByCollectionId(Integer collectionId) {
        List<FeaturedCollectionProduct> products = new ArrayList<>();
        String sql = "SELECT * FROM featured_collection_products WHERE collection_id = ? ORDER BY display_order";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, collectionId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FeaturedCollectionProduct fcp = new FeaturedCollectionProduct();
                fcp.setId(rs.getInt("id"));
                fcp.setCollectionId(rs.getInt("collection_id"));
                fcp.setProductId(rs.getInt("product_id"));
                fcp.setDisplayOrder(rs.getInt("display_order"));
                products.add(fcp);
            }
        } catch (SQLException e) {
            logger.error("Error finding featured collection products for id: " + collectionId, e);
        }
        return products;
    }

    @Override
    public FeaturedCollectionProduct create(FeaturedCollectionProduct fcp) {
        String sql = "INSERT INTO featured_collection_products (collection_id, product_id, display_order) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, fcp.getCollectionId());
            stmt.setInt(2, fcp.getProductId());
            stmt.setInt(3, fcp.getDisplayOrder() != null ? fcp.getDisplayOrder() : 0);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                fcp.setId(rs.getInt(1));
            }
            return fcp;
        } catch (SQLException e) {
            logger.error("Error creating featured collection product", e);
            return fcp;
        }
    }

    @Override
    public void deleteByCollectionId(Integer collectionId) {
        String sql = "DELETE FROM featured_collection_products WHERE collection_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, collectionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting featured collection products for id: " + collectionId, e);
        }
    }
}
