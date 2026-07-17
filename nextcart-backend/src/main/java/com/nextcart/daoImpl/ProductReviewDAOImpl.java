package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.ProductReviewDAO;
import com.nextcart.model.ProductReview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductReviewDAOImpl implements ProductReviewDAO {
    private static final Logger logger = LoggerFactory.getLogger(ProductReviewDAOImpl.class);

    @Override
    public Optional<ProductReview> findById(Integer reviewId) {
        String sql = "SELECT * FROM product_reviews WHERE review_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reviewId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractProductReview(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding review by id: {}", reviewId, e);
        }
        return Optional.empty();
    }

    @Override
    public List<ProductReview> findByProductId(Integer productId, int page, int size) {
        List<ProductReview> reviews = new ArrayList<>();
        String sql = "SELECT * FROM product_reviews WHERE product_id = ? AND approved = TRUE ORDER BY created_at DESC LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, size);
            stmt.setInt(3, (page - 1) * size);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reviews.add(extractProductReview(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding reviews by product id: {}", productId, e);
        }
        return reviews;
    }

    @Override
    public List<ProductReview> findByUserId(Integer userId) {
        List<ProductReview> reviews = new ArrayList<>();
        String sql = "SELECT * FROM product_reviews WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reviews.add(extractProductReview(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding reviews by user id: {}", userId, e);
        }
        return reviews;
    }

    @Override
    public List<ProductReview> findPendingApproval() {
        List<ProductReview> reviews = new ArrayList<>();
        String sql = "SELECT * FROM product_reviews WHERE approved = FALSE ORDER BY created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reviews.add(extractProductReview(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding pending reviews", e);
        }
        return reviews;
    }

    @Override
    public ProductReview create(ProductReview review) {
        String sql = "INSERT INTO product_reviews (product_id, user_id, rating, title, review, review_image, approved) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, review.getProductId());
            stmt.setInt(2, review.getUserId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getTitle());
            stmt.setString(5, review.getReview());
            stmt.setString(6, review.getReviewImage());
            stmt.setBoolean(7, review.getApproved() != null ? review.getApproved() : true);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                review.setReviewId(rs.getInt(1));
                return findById(review.getReviewId()).orElse(review);
            }
        } catch (SQLException e) {
            logger.error("Error creating review", e);
        }
        return review;
    }

    @Override
    public ProductReview update(ProductReview review) {
        String sql = "UPDATE product_reviews SET product_id = ?, user_id = ?, rating = ?, title = ?, review = ?, review_image = ?, approved = ? " +
                     "WHERE review_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, review.getProductId());
            stmt.setInt(2, review.getUserId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getTitle());
            stmt.setString(5, review.getReview());
            stmt.setString(6, review.getReviewImage());
            stmt.setBoolean(7, review.getApproved());
            stmt.setInt(8, review.getReviewId());
            stmt.executeUpdate();
            return findById(review.getReviewId()).orElse(review);
        } catch (SQLException e) {
            logger.error("Error updating review: {}", review.getReviewId(), e);
            return review;
        }
    }

    @Override
    public boolean delete(Integer reviewId) {
        String sql = "DELETE FROM product_reviews WHERE review_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reviewId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting review: {}", reviewId, e);
            return false;
        }
    }

    @Override
    public double getAverageRating(Integer productId) {
        String sql = "SELECT AVG(rating) as avg_rating FROM product_reviews WHERE product_id = ? AND approved = TRUE";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }
        } catch (SQLException e) {
            logger.error("Error getting average rating for product: {}", productId, e);
        }
        return 0.0;
    }

    @Override
    public int getReviewCount(Integer productId) {
        String sql = "SELECT COUNT(*) as count FROM product_reviews WHERE product_id = ? AND approved = TRUE";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            logger.error("Error getting review count for product: {}", productId, e);
        }
        return 0;
    }

    private ProductReview extractProductReview(ResultSet rs) throws SQLException {
        ProductReview review = new ProductReview();
        review.setReviewId(rs.getInt("review_id"));
        review.setProductId(rs.getInt("product_id"));
        review.setUserId(rs.getInt("user_id"));
        review.setRating(rs.getInt("rating"));
        review.setTitle(rs.getString("title"));
        review.setReview(rs.getString("review"));
        review.setReviewImage(rs.getString("review_image"));
        review.setApproved(rs.getBoolean("approved"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            review.setCreatedAt(createdAt.toLocalDateTime());
        }
        return review;
    }
}
