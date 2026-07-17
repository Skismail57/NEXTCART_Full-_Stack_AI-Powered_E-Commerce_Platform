package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.FeaturedCollectionDAO;
import com.nextcart.model.FeaturedCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FeaturedCollectionDAOImpl implements FeaturedCollectionDAO {
    private static final Logger logger = LoggerFactory.getLogger(FeaturedCollectionDAOImpl.class);

    @Override
    public Optional<FeaturedCollection> findById(Integer collectionId) {
        String sql = "SELECT * FROM featured_collections WHERE collection_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, collectionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractFeaturedCollection(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding featured collection by id: " + collectionId, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<FeaturedCollection> findBySlug(String slug) {
        String sql = "SELECT * FROM featured_collections WHERE slug = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, slug);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractFeaturedCollection(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding featured collection by slug: " + slug, e);
        }
        return Optional.empty();
    }

    @Override
    public List<FeaturedCollection> findAll() {
        List<FeaturedCollection> collections = new ArrayList<>();
        String sql = "SELECT * FROM featured_collections ORDER BY display_order";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                collections.add(extractFeaturedCollection(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all featured collections", e);
        }
        return collections;
    }

    @Override
    public List<FeaturedCollection> findActive() {
        List<FeaturedCollection> collections = new ArrayList<>();
        String sql = "SELECT * FROM featured_collections WHERE status = 'ACTIVE' ORDER BY display_order";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                collections.add(extractFeaturedCollection(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding active featured collections", e);
        }
        return collections;
    }

    @Override
    public FeaturedCollection create(FeaturedCollection collection) {
        String sql = "INSERT INTO featured_collections (name, slug, description, image, status, display_order) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, collection.getName());
            stmt.setString(2, collection.getSlug());
            stmt.setString(3, collection.getDescription());
            stmt.setString(4, collection.getImage());
            stmt.setString(5, collection.getStatus() != null ? collection.getStatus() : "ACTIVE");
            stmt.setInt(6, collection.getDisplayOrder() != null ? collection.getDisplayOrder() : 0);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                collection.setCollectionId(rs.getInt(1));
            }
            return findById(collection.getCollectionId()).orElse(collection);
        } catch (SQLException e) {
            logger.error("Error creating featured collection", e);
            return collection;
        }
    }

    @Override
    public FeaturedCollection update(FeaturedCollection collection) {
        String sql = "UPDATE featured_collections SET name = ?, slug = ?, description = ?, image = ?, status = ?, display_order = ? WHERE collection_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collection.getName());
            stmt.setString(2, collection.getSlug());
            stmt.setString(3, collection.getDescription());
            stmt.setString(4, collection.getImage());
            stmt.setString(5, collection.getStatus());
            stmt.setInt(6, collection.getDisplayOrder());
            stmt.setInt(7, collection.getCollectionId());
            stmt.executeUpdate();
            return findById(collection.getCollectionId()).orElse(collection);
        } catch (SQLException e) {
            logger.error("Error updating featured collection", e);
            return collection;
        }
    }

    @Override
    public void delete(Integer collectionId) {
        String sql = "DELETE FROM featured_collections WHERE collection_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, collectionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting featured collection: " + collectionId, e);
        }
    }

    private FeaturedCollection extractFeaturedCollection(ResultSet rs) throws SQLException {
        FeaturedCollection collection = new FeaturedCollection();
        collection.setCollectionId(rs.getInt("collection_id"));
        collection.setName(rs.getString("name"));
        collection.setSlug(rs.getString("slug"));
        collection.setDescription(rs.getString("description"));
        collection.setImage(rs.getString("image"));
        collection.setStatus(rs.getString("status"));
        collection.setDisplayOrder(rs.getInt("display_order"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            collection.setCreatedAt(createdAt.toLocalDateTime());
        }
        return collection;
    }
}
