package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.RolePermissionDAO;
import com.nextcart.model.RolePermission;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RolePermissionDAOImpl implements RolePermissionDAO {

    @Override
    public Optional<RolePermission> findById(Integer id) {
        String sql = "SELECT * FROM role_permissions WHERE role_permission_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractRolePermission(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<RolePermission> findByRoleId(Integer roleId) {
        List<RolePermission> rolePermissions = new ArrayList<>();
        String sql = "SELECT * FROM role_permissions WHERE role_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roleId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rolePermissions.add(extractRolePermission(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rolePermissions;
    }

    @Override
    public List<RolePermission> findByPermissionId(Integer permissionId) {
        List<RolePermission> rolePermissions = new ArrayList<>();
        String sql = "SELECT * FROM role_permissions WHERE permission_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, permissionId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rolePermissions.add(extractRolePermission(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rolePermissions;
    }

    @Override
    public List<RolePermission> findAll() {
        List<RolePermission> rolePermissions = new ArrayList<>();
        String sql = "SELECT * FROM role_permissions";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                rolePermissions.add(extractRolePermission(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rolePermissions;
    }

    @Override
    public RolePermission create(RolePermission rolePermission) {
        String sql = "INSERT INTO role_permissions (role_id, permission_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, rolePermission.getRoleId());
            pstmt.setInt(2, rolePermission.getPermissionId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    rolePermission.setRolePermissionId(rs.getInt(1));
                    return findById(rolePermission.getRolePermissionId()).orElse(rolePermission);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteByRoleIdAndPermissionId(Integer roleId, Integer permissionId) {
        String sql = "DELETE FROM role_permissions WHERE role_id = ? AND permission_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roleId);
            pstmt.setInt(2, permissionId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM role_permissions WHERE role_permission_id = ?";
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

    private RolePermission extractRolePermission(ResultSet rs) throws SQLException {
        RolePermission rp = new RolePermission();
        rp.setRolePermissionId(rs.getInt("role_permission_id"));
        rp.setRoleId(rs.getInt("role_id"));
        rp.setPermissionId(rs.getInt("permission_id"));
        rp.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return rp;
    }
}
