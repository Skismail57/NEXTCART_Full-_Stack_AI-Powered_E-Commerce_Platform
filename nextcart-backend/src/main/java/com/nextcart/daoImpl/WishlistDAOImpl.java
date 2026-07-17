package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.ProductDAO;
import com.nextcart.dao.WishlistDAO;
import com.nextcart.model.Product;
import com.nextcart.model.Wishlist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WishlistDAOImpl implements WishlistDAO {
    private static final Logger logger = LoggerFactory.getLogger(WishlistDAOImpl.class);
    private final ProductDAO productDAO = new ProductDAOImpl();

    @Override
    public Optional<Wishlist> findById(Integer wishlistId) {
        String sql = "SELECT * FROM wishlist WHERE wishlist_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, wishlistId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Wishlist wishlist = extractWishlist(rs);
                Optional<Product> productOpt = productDAO.findById(wishlist.getProductId());
                productOpt.ifPresent(wishlist::setProduct);
                return Optional.of(wishlist);
            }
        } catch (SQLException e) {
            logger.error("Error finding wishlist item by id: {}", wishlistId, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Wishlist> findByUserId(Integer userId) {
        List<Wishlist> wishlistItems = new ArrayList<>();
        String sql = "SELECT * FROM wishlist WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Wishlist wishlist = extractWishlist(rs);
                Optional<Product> productOpt = productDAO.findById(wishlist.getProductId());
                productOpt.ifPresent(wishlist::setProduct);
                wishlistItems.add(wishlist);
            }
        } catch (SQLException e) {
            logger.error("Error finding wishlist for user id: {}", userId, e);
        }
        return wishlistItems;
    }

    @Override
    public Optional<Wishlist> findByUserIdAndProductId(Integer userId, Integer productId) {
        String sql = "SELECT * FROM wishlist WHERE user_id = ? AND product_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Wishlist wishlist = extractWishlist(rs);
                Optional<Product> productOpt = productDAO.findById(wishlist.getProductId());
                productOpt.ifPresent(wishlist::setProduct);
                return Optional.of(wishlist);
            }
        } catch (SQLException e) {
            logger.error("Error finding wishlist item for user {} and product {}", userId, productId, e);
        }
        return Optional.empty();
    }

    @Override
    public Wishlist create(Wishlist wishlist) {
        String sql = "INSERT INTO wishlist (user_id, product_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, wishlist.getUserId());
            stmt.setInt(2, wishlist.getProductId());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                wishlist.setWishlistId(rs.getInt(1));
                return findById(wishlist.getWishlistId()).orElse(wishlist);
            }
        } catch (SQLException e) {
            logger.error("Error adding to wishlist", e);
        }
        return wishlist;
    }

    @Override
    public boolean delete(Integer wishlistId) {
        String sql = "DELETE FROM wishlist WHERE wishlist_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, wishlistId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting wishlist item: {}", wishlistId, e);
            return false;
        }
    }

    @Override
    public boolean deleteByUserIdAndProductId(Integer userId, Integer productId) {
        String sql = "DELETE FROM wishlist WHERE user_id = ? AND product_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting wishlist item for user {} and product {}", userId, productId, e);
            return false;
        }
    }

    @Override
    public boolean exists(Integer userId, Integer productId) {
        String sql = "SELECT 1 FROM wishlist WHERE user_id = ? AND product_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            logger.error("Error checking wishlist existence", e);
            return false;
        }
    }

    private Wishlist extractWishlist(ResultSet rs) throws SQLException {
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(rs.getInt("wishlist_id"));
        wishlist.setUserId(rs.getInt("user_id"));
        wishlist.setProductId(rs.getInt("product_id"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            wishlist.setCreatedAt(createdAt.toLocalDateTime());
        }
        return wishlist;
    }
}
