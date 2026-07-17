package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.OfferDAO;
import com.nextcart.model.Offer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OfferDAOImpl implements OfferDAO {
    private static final Logger logger = LoggerFactory.getLogger(OfferDAOImpl.class);

    @Override
    public Optional<Offer> findById(Integer offerId) {
        String sql = "SELECT * FROM offers WHERE offer_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, offerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractOffer(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding offer by id: " + offerId, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Offer> findAll() {
        List<Offer> offers = new ArrayList<>();
        String sql = "SELECT * FROM offers ORDER BY title";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                offers.add(extractOffer(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding all offers", e);
        }

        return offers;
    }

    @Override
    public List<Offer> findActive() {
        List<Offer> offers = new ArrayList<>();
        String sql = "SELECT * FROM offers WHERE status = 'ACTIVE' AND CURDATE() BETWEEN start_date AND end_date ORDER BY title";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                offers.add(extractOffer(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding active offers", e);
        }

        return offers;
    }

    @Override
    public Offer create(Offer offer) {
        String sql = "INSERT INTO offers (title, discount_percentage, start_date, end_date, status) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, offer.getTitle());
            stmt.setBigDecimal(2, offer.getDiscountPercentage());
            stmt.setDate(3, offer.getStartDate() != null ? Date.valueOf(offer.getStartDate()) : null);
            stmt.setDate(4, offer.getEndDate() != null ? Date.valueOf(offer.getEndDate()) : null);
            stmt.setString(5, offer.getStatus() != null ? offer.getStatus() : "ACTIVE");
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                offer.setOfferId(rs.getInt(1));
            }
            
            return findById(offer.getOfferId()).orElse(offer);
        } catch (SQLException e) {
            logger.error("Error creating offer", e);
            return offer;
        }
    }

    @Override
    public Offer update(Offer offer) {
        String sql = "UPDATE offers SET title = ?, discount_percentage = ?, start_date = ?, end_date = ?, status = ? WHERE offer_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, offer.getTitle());
            stmt.setBigDecimal(2, offer.getDiscountPercentage());
            stmt.setDate(3, offer.getStartDate() != null ? Date.valueOf(offer.getStartDate()) : null);
            stmt.setDate(4, offer.getEndDate() != null ? Date.valueOf(offer.getEndDate()) : null);
            stmt.setString(5, offer.getStatus());
            stmt.setInt(6, offer.getOfferId());
            
            stmt.executeUpdate();
            return findById(offer.getOfferId()).orElse(offer);
        } catch (SQLException e) {
            logger.error("Error updating offer", e);
            return offer;
        }
    }

    @Override
    public void delete(Integer offerId) {
        String sql = "DELETE FROM offers WHERE offer_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, offerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting offer", e);
        }
    }

    private Offer extractOffer(ResultSet rs) throws SQLException {
        Offer offer = new Offer();
        offer.setOfferId(rs.getInt("offer_id"));
        offer.setTitle(rs.getString("title"));
        offer.setDiscountPercentage(rs.getBigDecimal("discount_percentage"));
        
        Date startDate = rs.getDate("start_date");
        if (startDate != null) {
            offer.setStartDate(startDate.toLocalDate());
        }
        
        Date endDate = rs.getDate("end_date");
        if (endDate != null) {
            offer.setEndDate(endDate.toLocalDate());
        }
        
        offer.setStatus(rs.getString("status"));

        return offer;
    }
}
