package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.CouponDAO;
import com.nextcart.model.Coupon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CouponDAOImpl implements CouponDAO {
    private static final Logger logger = LoggerFactory.getLogger(CouponDAOImpl.class);

    @Override
    public Optional<Coupon> findById(Integer couponId) {
        String sql = "SELECT * FROM coupons WHERE coupon_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, couponId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractCoupon(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding coupon by id: {}", couponId, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Coupon> findByCouponCode(String couponCode) {
        String sql = "SELECT * FROM coupons WHERE coupon_code = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, couponCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractCoupon(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding coupon by code: {}", couponCode, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Coupon> findAll() {
        List<Coupon> coupons = new ArrayList<>();
        String sql = "SELECT * FROM coupons ORDER BY created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                coupons.add(extractCoupon(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all coupons", e);
        }
        return coupons;
    }

    @Override
    public List<Coupon> findActive() {
        List<Coupon> coupons = new ArrayList<>();
        String sql = "SELECT * FROM coupons WHERE status = 'ACTIVE' AND expiry_date >= ? ORDER BY created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                coupons.add(extractCoupon(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding active coupons", e);
        }
        return coupons;
    }

    @Override
    public Coupon create(Coupon coupon) {
        String sql = "INSERT INTO coupons (coupon_code, discount_type, discount_value, minimum_amount, " +
                     "maximum_discount, expiry_date, usage_limit, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, coupon.getCouponCode());
            stmt.setString(2, coupon.getDiscountType());
            stmt.setBigDecimal(3, coupon.getDiscountValue());
            stmt.setBigDecimal(4, coupon.getMinimumAmount());
            stmt.setBigDecimal(5, coupon.getMaximumDiscount());
            stmt.setDate(6, Date.valueOf(coupon.getExpiryDate()));
            stmt.setObject(7, coupon.getUsageLimit(), Types.INTEGER);
            stmt.setString(8, coupon.getStatus() != null ? coupon.getStatus() : "ACTIVE");
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                coupon.setCouponId(rs.getInt(1));
                return findById(coupon.getCouponId()).orElse(coupon);
            }
        } catch (SQLException e) {
            logger.error("Error creating coupon", e);
        }
        return coupon;
    }

    @Override
    public Coupon update(Coupon coupon) {
        String sql = "UPDATE coupons SET coupon_code = ?, discount_type = ?, discount_value = ?, minimum_amount = ?, " +
                     "maximum_discount = ?, expiry_date = ?, usage_limit = ?, status = ? WHERE coupon_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, coupon.getCouponCode());
            stmt.setString(2, coupon.getDiscountType());
            stmt.setBigDecimal(3, coupon.getDiscountValue());
            stmt.setBigDecimal(4, coupon.getMinimumAmount());
            stmt.setBigDecimal(5, coupon.getMaximumDiscount());
            stmt.setDate(6, Date.valueOf(coupon.getExpiryDate()));
            stmt.setObject(7, coupon.getUsageLimit(), Types.INTEGER);
            stmt.setString(8, coupon.getStatus());
            stmt.setInt(9, coupon.getCouponId());
            stmt.executeUpdate();
            return findById(coupon.getCouponId()).orElse(coupon);
        } catch (SQLException e) {
            logger.error("Error updating coupon: {}", coupon.getCouponId(), e);
            return coupon;
        }
    }

    @Override
    public boolean delete(Integer couponId) {
        String sql = "DELETE FROM coupons WHERE coupon_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, couponId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting coupon: {}", couponId, e);
            return false;
        }
    }

    @Override
    public boolean isValid(String couponCode) {
        Optional<Coupon> coupon = findByCouponCode(couponCode);
        if (coupon.isEmpty()) {
            return false;
        }
        Coupon c = coupon.get();
        return "ACTIVE".equals(c.getStatus()) && c.getExpiryDate().isAfter(LocalDate.now().minusDays(1));
    }

    private Coupon extractCoupon(ResultSet rs) throws SQLException {
        Coupon coupon = new Coupon();
        coupon.setCouponId(rs.getInt("coupon_id"));
        coupon.setCouponCode(rs.getString("coupon_code"));
        coupon.setDiscountType(rs.getString("discount_type"));
        coupon.setDiscountValue(rs.getBigDecimal("discount_value"));
        coupon.setMinimumAmount(rs.getBigDecimal("minimum_amount"));
        coupon.setMaximumDiscount(rs.getBigDecimal("maximum_discount"));
        Date expiryDate = rs.getDate("expiry_date");
        if (expiryDate != null) {
            coupon.setExpiryDate(expiryDate.toLocalDate());
        }
        coupon.setUsageLimit((Integer) rs.getObject("usage_limit"));
        coupon.setStatus(rs.getString("status"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            coupon.setCreatedAt(createdAt.toLocalDateTime());
        }
        return coupon;
    }
}
