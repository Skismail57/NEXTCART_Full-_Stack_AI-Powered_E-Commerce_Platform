package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.FlashSaleDAO;
import com.nextcart.model.FlashSale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlashSaleDAOImpl implements FlashSaleDAO {
    private static final Logger logger = LoggerFactory.getLogger(FlashSaleDAOImpl.class);

    @Override
    public Optional<FlashSale> findById(Integer flashSaleId) {
        String sql = "SELECT * FROM flash_sales WHERE flash_sale_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, flashSaleId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractFlashSale(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding flash sale by id: " + flashSaleId, e);
        }
        return Optional.empty();
    }

    @Override
    public List<FlashSale> findAll() {
        List<FlashSale> flashSales = new ArrayList<>();
        String sql = "SELECT * FROM flash_sales ORDER BY start_time DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                flashSales.add(extractFlashSale(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all flash sales", e);
        }
        return flashSales;
    }

    @Override
    public List<FlashSale> findActive() {
        List<FlashSale> flashSales = new ArrayList<>();
        String sql = "SELECT * FROM flash_sales WHERE status = 'ACTIVE' " +
                     "AND start_time <= CURRENT_TIMESTAMP AND end_time >= CURRENT_TIMESTAMP " +
                     "ORDER BY start_time DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                flashSales.add(extractFlashSale(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding active flash sales", e);
        }
        return flashSales;
    }

    @Override
    public FlashSale create(FlashSale flashSale) {
        String sql = "INSERT INTO flash_sales (name, start_time, end_time, banner_image, discount_percentage, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, flashSale.getName());
            stmt.setTimestamp(2, flashSale.getStartTime() != null ? Timestamp.valueOf(flashSale.getStartTime()) : null);
            stmt.setTimestamp(3, flashSale.getEndTime() != null ? Timestamp.valueOf(flashSale.getEndTime()) : null);
            stmt.setString(4, flashSale.getBannerImage());
            stmt.setBigDecimal(5, flashSale.getDiscountPercentage());
            stmt.setString(6, flashSale.getStatus() != null ? flashSale.getStatus() : "SCHEDULED");
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                flashSale.setFlashSaleId(rs.getInt(1));
            }
            return findById(flashSale.getFlashSaleId()).orElse(flashSale);
        } catch (SQLException e) {
            logger.error("Error creating flash sale", e);
            return flashSale;
        }
    }

    @Override
    public FlashSale update(FlashSale flashSale) {
        String sql = "UPDATE flash_sales SET name = ?, start_time = ?, end_time = ?, banner_image = ?, discount_percentage = ?, status = ? WHERE flash_sale_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, flashSale.getName());
            stmt.setTimestamp(2, flashSale.getStartTime() != null ? Timestamp.valueOf(flashSale.getStartTime()) : null);
            stmt.setTimestamp(3, flashSale.getEndTime() != null ? Timestamp.valueOf(flashSale.getEndTime()) : null);
            stmt.setString(4, flashSale.getBannerImage());
            stmt.setBigDecimal(5, flashSale.getDiscountPercentage());
            stmt.setString(6, flashSale.getStatus());
            stmt.setInt(7, flashSale.getFlashSaleId());
            stmt.executeUpdate();
            return findById(flashSale.getFlashSaleId()).orElse(flashSale);
        } catch (SQLException e) {
            logger.error("Error updating flash sale", e);
            return flashSale;
        }
    }

    @Override
    public void delete(Integer flashSaleId) {
        String sql = "DELETE FROM flash_sales WHERE flash_sale_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, flashSaleId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting flash sale: " + flashSaleId, e);
        }
    }

    private FlashSale extractFlashSale(ResultSet rs) throws SQLException {
        FlashSale flashSale = new FlashSale();
        flashSale.setFlashSaleId(rs.getInt("flash_sale_id"));
        flashSale.setName(rs.getString("name"));
        Timestamp startTime = rs.getTimestamp("start_time");
        if (startTime != null) {
            flashSale.setStartTime(startTime.toLocalDateTime());
        }
        Timestamp endTime = rs.getTimestamp("end_time");
        if (endTime != null) {
            flashSale.setEndTime(endTime.toLocalDateTime());
        }
        flashSale.setBannerImage(rs.getString("banner_image"));
        flashSale.setDiscountPercentage(rs.getBigDecimal("discount_percentage"));
        flashSale.setStatus(rs.getString("status"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            flashSale.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            flashSale.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return flashSale;
    }
}
