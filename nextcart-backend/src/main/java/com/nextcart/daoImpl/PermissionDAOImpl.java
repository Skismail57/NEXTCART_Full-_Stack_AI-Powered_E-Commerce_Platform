package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.PermissionDAO;
import com.nextcart.model.Permission;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PermissionDAOImpl implements PermissionDAO {

    @Override
    public Optional<Permission> findById(Integer id) {
        String sql = "SELECT * FROM permissions WHERE permission_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractPermission(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Permission> findBySlug(String slug) {
        String sql = "SELECT * FROM permissions WHERE slug = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, slug);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractPermission(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Permission> findAll() {
        List<Permission> permissions = new ArrayList<>();
        String sql = "SELECT * FROM permissions ORDER BY name";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                permissions.add(extractPermission(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return permissions;
    }

    @Override
    public Permission create(Permission permission) {
        String sql = "INSERT INTO permissions (name, slug, description) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, permission.getName());
            pstmt.setString(2, permission.getSlug());
            pstmt.setString(3, permission.getDescription());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    permission.setPermissionId(rs.getInt(1));
                    return findById(permission.getPermissionId()).orElse(permission);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Permission update(Permission permission) {
        String sql = "UPDATE permissions SET name = ?, slug = ?, description = ? WHERE permission_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, permission.getName());
            pstmt.setString(2, permission.getSlug());
            pstmt.setString(3, permission.getDescription());
            pstmt.setInt(4, permission.getPermissionId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return findById(permission.getPermissionId()).orElse(permission);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM permissions WHERE permission_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Permission extractPermission(ResultSet rs) throws SQLException {
        Permission permission = new Permission();
        permission.setPermissionId(rs.getInt("permission_id"));
        permission.setName(rs.getString("name"));
        permission.setSlug(rs.getString("slug"));
        permission.setDescription(rs.getString("description"));
        permission.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return permission;
    }
}
