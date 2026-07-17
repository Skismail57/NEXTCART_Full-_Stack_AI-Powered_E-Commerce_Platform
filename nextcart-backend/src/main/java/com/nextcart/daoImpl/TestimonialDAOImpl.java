package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.TestimonialDAO;
import com.nextcart.model.Testimonial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestimonialDAOImpl implements TestimonialDAO {
    private static final Logger logger = LoggerFactory.getLogger(TestimonialDAOImpl.class);

    @Override
    public Optional<Testimonial> findById(Integer testimonialId) {
        String sql = "SELECT * FROM testimonials WHERE testimonial_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, testimonialId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractTestimonial(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding testimonial by id: " + testimonialId, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Testimonial> findAll() {
        List<Testimonial> testimonials = new ArrayList<>();
        String sql = "SELECT * FROM testimonials ORDER BY display_order";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                testimonials.add(extractTestimonial(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all testimonials", e);
        }
        return testimonials;
    }

    @Override
    public List<Testimonial> findActive() {
        List<Testimonial> testimonials = new ArrayList<>();
        String sql = "SELECT * FROM testimonials WHERE status = 'ACTIVE' ORDER BY display_order";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                testimonials.add(extractTestimonial(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding active testimonials", e);
        }
        return testimonials;
    }

    @Override
    public Testimonial create(Testimonial testimonial) {
        String sql = "INSERT INTO testimonials (customer_name, customer_avatar, rating, text, status, display_order) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, testimonial.getCustomerName());
            stmt.setString(2, testimonial.getCustomerAvatar());
            stmt.setInt(3, testimonial.getRating());
            stmt.setString(4, testimonial.getText());
            stmt.setString(5, testimonial.getStatus() != null ? testimonial.getStatus() : "ACTIVE");
            stmt.setInt(6, testimonial.getDisplayOrder() != null ? testimonial.getDisplayOrder() : 0);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                testimonial.setTestimonialId(rs.getInt(1));
            }
            return findById(testimonial.getTestimonialId()).orElse(testimonial);
        } catch (SQLException e) {
            logger.error("Error creating testimonial", e);
            return testimonial;
        }
    }

    @Override
    public Testimonial update(Testimonial testimonial) {
        String sql = "UPDATE testimonials SET customer_name = ?, customer_avatar = ?, rating = ?, text = ?, status = ?, display_order = ? WHERE testimonial_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, testimonial.getCustomerName());
            stmt.setString(2, testimonial.getCustomerAvatar());
            stmt.setInt(3, testimonial.getRating());
            stmt.setString(4, testimonial.getText());
            stmt.setString(5, testimonial.getStatus());
            stmt.setInt(6, testimonial.getDisplayOrder());
            stmt.setInt(7, testimonial.getTestimonialId());
            stmt.executeUpdate();
            return findById(testimonial.getTestimonialId()).orElse(testimonial);
        } catch (SQLException e) {
            logger.error("Error updating testimonial", e);
            return testimonial;
        }
    }

    @Override
    public void delete(Integer testimonialId) {
        String sql = "DELETE FROM testimonials WHERE testimonial_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, testimonialId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting testimonial: " + testimonialId, e);
        }
    }

    private Testimonial extractTestimonial(ResultSet rs) throws SQLException {
        Testimonial testimonial = new Testimonial();
        testimonial.setTestimonialId(rs.getInt("testimonial_id"));
        testimonial.setCustomerName(rs.getString("customer_name"));
        testimonial.setCustomerAvatar(rs.getString("customer_avatar"));
        testimonial.setRating(rs.getInt("rating"));
        testimonial.setText(rs.getString("text"));
        testimonial.setStatus(rs.getString("status"));
        testimonial.setDisplayOrder(rs.getInt("display_order"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            testimonial.setCreatedAt(createdAt.toLocalDateTime());
        }
        return testimonial;
    }
}
