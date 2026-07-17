package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.RoleDAO;
import com.nextcart.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleDAOImpl implements RoleDAO {
    private static final Logger logger = LoggerFactory.getLogger(RoleDAOImpl.class);

    @Override
    public Optional<Role> findById(Integer roleId) {
        String sql = "SELECT * FROM roles WHERE role_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roleId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractRole(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding role by id: {}", roleId, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Role> findByRoleName(String roleName) {
        String sql = "SELECT * FROM roles WHERE role_name = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, roleName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractRole(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding role by name: {}", roleName, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM roles ORDER BY created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                roles.add(extractRole(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all roles", e);
        }

        return roles;
    }

    @Override
    public Role create(Role role) {
        String sql = "INSERT INTO roles (role_name, description) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, role.getRoleName());
            stmt.setString(2, role.getDescription());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                role.setRoleId(rs.getInt(1));
                return findById(role.getRoleId()).orElse(role);
            }
        } catch (SQLException e) {
            logger.error("Error creating role", e);
        }
        return role;
    }

    @Override
    public Role update(Role role) {
        String sql = "UPDATE roles SET role_name = ?, description = ? WHERE role_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, role.getRoleName());
            stmt.setString(2, role.getDescription());
            stmt.setInt(3, role.getRoleId());
            stmt.executeUpdate();
            return findById(role.getRoleId()).orElse(role);
        } catch (SQLException e) {
            logger.error("Error updating role: {}", role.getRoleId(), e);
            return role;
        }
    }

    @Override
    public boolean delete(Integer roleId) {
        String sql = "DELETE FROM roles WHERE role_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roleId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting role: {}", roleId, e);
            return false;
        }
    }

    private Role extractRole(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setRoleId(rs.getInt("role_id"));
        role.setRoleName(rs.getString("role_name"));
        role.setDescription(rs.getString("description"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            role.setCreatedAt(createdAt.toLocalDateTime());
        }
        return role;
    }
}
