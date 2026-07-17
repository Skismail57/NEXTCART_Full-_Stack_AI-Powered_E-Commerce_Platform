package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.UserAddressDAO;
import com.nextcart.model.UserAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserAddressDAOImpl implements UserAddressDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserAddressDAOImpl.class);

    @Override
    public Optional<UserAddress> findById(Integer addressId) {
        String sql = "SELECT * FROM user_addresses WHERE address_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, addressId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractUserAddress(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding address by id: {}", addressId, e);
        }
        return Optional.empty();
    }

    @Override
    public List<UserAddress> findByUserId(Integer userId) {
        List<UserAddress> addresses = new ArrayList<>();
        String sql = "SELECT * FROM user_addresses WHERE user_id = ? ORDER BY is_default DESC, created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                addresses.add(extractUserAddress(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding addresses by user id: {}", userId, e);
        }
        return addresses;
    }

    @Override
    public Optional<UserAddress> findDefaultByUserId(Integer userId) {
        String sql = "SELECT * FROM user_addresses WHERE user_id = ? AND is_default = TRUE";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractUserAddress(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding default address for user: {}", userId, e);
        }
        return Optional.empty();
    }

    @Override
    public UserAddress create(UserAddress address) {
        String sql = "INSERT INTO user_addresses (user_id, full_name, mobile, address_line1, address_line2, " +
                     "landmark, city, state, country, postal_code, address_type, is_default) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, address.getUserId());
            stmt.setString(2, address.getFullName());
            stmt.setString(3, address.getMobile());
            stmt.setString(4, address.getAddressLine1());
            stmt.setString(5, address.getAddressLine2());
            stmt.setString(6, address.getLandmark());
            stmt.setString(7, address.getCity());
            stmt.setString(8, address.getState());
            stmt.setString(9, address.getCountry() != null ? address.getCountry() : "INDIA");
            stmt.setString(10, address.getPostalCode());
            stmt.setString(11, address.getAddressType() != null ? address.getAddressType() : "HOME");
            stmt.setBoolean(12, address.getIsDefault() != null ? address.getIsDefault() : false);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                address.setAddressId(rs.getInt(1));
                if (address.getIsDefault()) {
                    unsetOtherDefaults(address.getUserId(), address.getAddressId(), conn);
                }
                return findById(address.getAddressId()).orElse(address);
            }
        } catch (SQLException e) {
            logger.error("Error creating address", e);
        }
        return address;
    }

    @Override
    public UserAddress update(UserAddress address) {
        String sql = "UPDATE user_addresses SET full_name = ?, mobile = ?, address_line1 = ?, address_line2 = ?, " +
                     "landmark = ?, city = ?, state = ?, country = ?, postal_code = ?, address_type = ?, is_default = ? " +
                     "WHERE address_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, address.getFullName());
            stmt.setString(2, address.getMobile());
            stmt.setString(3, address.getAddressLine1());
            stmt.setString(4, address.getAddressLine2());
            stmt.setString(5, address.getLandmark());
            stmt.setString(6, address.getCity());
            stmt.setString(7, address.getState());
            stmt.setString(8, address.getCountry());
            stmt.setString(9, address.getPostalCode());
            stmt.setString(10, address.getAddressType());
            stmt.setBoolean(11, address.getIsDefault());
            stmt.setInt(12, address.getAddressId());
            stmt.executeUpdate();

            if (address.getIsDefault()) {
                unsetOtherDefaults(address.getUserId(), address.getAddressId(), conn);
            }

            return findById(address.getAddressId()).orElse(address);
        } catch (SQLException e) {
            logger.error("Error updating address: {}", address.getAddressId(), e);
            return address;
        }
    }

    @Override
    public boolean delete(Integer addressId) {
        String sql = "DELETE FROM user_addresses WHERE address_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, addressId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting address: {}", addressId, e);
            return false;
        }
    }

    private void unsetOtherDefaults(Integer userId, Integer excludeAddressId, Connection conn) throws SQLException {
        String sql = "UPDATE user_addresses SET is_default = FALSE WHERE user_id = ? AND address_id != ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, excludeAddressId);
            stmt.executeUpdate();
        }
    }

    private UserAddress extractUserAddress(ResultSet rs) throws SQLException {
        UserAddress address = new UserAddress();
        address.setAddressId(rs.getInt("address_id"));
        address.setUserId(rs.getInt("user_id"));
        address.setFullName(rs.getString("full_name"));
        address.setMobile(rs.getString("mobile"));
        address.setAddressLine1(rs.getString("address_line1"));
        address.setAddressLine2(rs.getString("address_line2"));
        address.setLandmark(rs.getString("landmark"));
        address.setCity(rs.getString("city"));
        address.setState(rs.getString("state"));
        address.setCountry(rs.getString("country"));
        address.setPostalCode(rs.getString("postal_code"));
        address.setAddressType(rs.getString("address_type"));
        address.setIsDefault(rs.getBoolean("is_default"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            address.setCreatedAt(createdAt.toLocalDateTime());
        }
        return address;
    }
}
