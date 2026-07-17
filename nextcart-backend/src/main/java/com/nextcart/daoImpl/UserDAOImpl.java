package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.UserDAO;
import com.nextcart.model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public Optional<User> findById(Integer userId) {
        String sql = "SELECT u.*, r.role_name FROM users u LEFT JOIN roles r ON u.role_id = r.role_id WHERE u.user_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractUser(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding user by id: " + userId, e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT u.*, r.role_name FROM users u LEFT JOIN roles r ON u.role_id = r.role_id WHERE u.email = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractUser(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding user by email: " + email, e);
        }

        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.*, r.role_name FROM users u LEFT JOIN roles r ON u.role_id = r.role_id ORDER BY u.created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(extractUser(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding all users", e);
        }

        return users;
    }

    @Override
    public User create(User user) {
        String sql = "INSERT INTO users (role_id, first_name, last_name, email, mobile, password, profile_image, gender, date_of_birth, status, email_verified, mobile_verified) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, user.getRoleId() != null ? user.getRoleId() : 1);
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getMobile());
            stmt.setString(6, user.getPassword());
            stmt.setString(7, user.getProfileImage());
            stmt.setString(8, user.getGender());

            if (user.getDateOfBirth() != null) {
                stmt.setDate(9, Date.valueOf(user.getDateOfBirth()));
            } else {
                stmt.setNull(9, Types.DATE);
            }

            stmt.setString(10, user.getStatus() != null ? user.getStatus() : "ACTIVE");
            stmt.setBoolean(11, user.getEmailVerified() != null ? user.getEmailVerified() : false);
            stmt.setBoolean(12, user.getMobileVerified() != null ? user.getMobileVerified() : false);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                user.setUserId(rs.getInt(1));
                return findById(user.getUserId()).orElse(user);
            }

        } catch (SQLException e) {
            logger.error("Error creating user", e);
        }

        return user;
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, mobile = ?, profile_image = ?, gender = ?, date_of_birth = ?, status = ?, email_verified = ?, mobile_verified = ? " +
                     "WHERE user_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getMobile());
            stmt.setString(4, user.getProfileImage());
            stmt.setString(5, user.getGender());

            if (user.getDateOfBirth() != null) {
                stmt.setDate(6, Date.valueOf(user.getDateOfBirth()));
            } else {
                stmt.setNull(6, Types.DATE);
            }

            stmt.setString(7, user.getStatus());
            stmt.setBoolean(8, user.getEmailVerified());
            stmt.setBoolean(9, user.getMobileVerified());
            stmt.setInt(10, user.getUserId());

            stmt.executeUpdate();

            return findById(user.getUserId()).orElse(user);

        } catch (SQLException e) {
            logger.error("Error updating user: " + user.getUserId(), e);
            return user;
        }
    }

    @Override
    public boolean delete(Integer userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.error("Error deleting user: " + userId, e);
            return false;
        }
    }

    @Override
    public Optional<User> authenticate(String email, String password) {
        Optional<User> userOpt = findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (BCrypt.checkpw(password, user.getPassword())) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setRoleId(rs.getInt("role_id"));
        user.setRoleName(rs.getString("role_name"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setMobile(rs.getString("mobile"));
        user.setPassword(rs.getString("password"));
        user.setProfileImage(rs.getString("profile_image"));
        user.setGender(rs.getString("gender"));

        Date dob = rs.getDate("date_of_birth");
        if (dob != null) {
            user.setDateOfBirth(dob.toLocalDate());
        }

        user.setStatus(rs.getString("status"));
        user.setEmailVerified(rs.getBoolean("email_verified"));
        user.setMobileVerified(rs.getBoolean("mobile_verified"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            user.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            user.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return user;
    }
}
