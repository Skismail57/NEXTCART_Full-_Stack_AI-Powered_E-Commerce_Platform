package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.MenuItemDAO;
import com.nextcart.model.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuItemDAOImpl implements MenuItemDAO {

    @Override
    public Optional<MenuItem> findById(Integer id) {
        String sql = "SELECT * FROM menu_items WHERE menu_item_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractMenuItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<MenuItem> findAll() {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM menu_items ORDER BY menu_type, display_order, title";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                menuItems.add(extractMenuItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItems;
    }

    @Override
    public List<MenuItem> findActive() {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE status = 'ACTIVE' ORDER BY menu_type, display_order, title";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                menuItems.add(extractMenuItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItems;
    }

    @Override
    public List<MenuItem> findByMenuType(String menuType) {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE menu_type = ? AND status = 'ACTIVE' ORDER BY display_order, title";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, menuType);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                menuItems.add(extractMenuItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItems;
    }

    @Override
    public List<MenuItem> findByParentId(Integer parentId) {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE parent_id = ? AND status = 'ACTIVE' ORDER BY display_order, title";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, parentId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                menuItems.add(extractMenuItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItems;
    }

    @Override
    public List<MenuItem> findRootMenuItems(String menuType) {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE parent_id IS NULL AND menu_type = ? AND status = 'ACTIVE' ORDER BY display_order, title";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, menuType);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                MenuItem menuItem = extractMenuItem(rs);
                menuItem.setChildren(findByParentId(menuItem.getMenuItemId()));
                menuItems.add(menuItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItems;
    }

    @Override
    public MenuItem create(MenuItem menuItem) {
        String sql = "INSERT INTO menu_items (parent_id, title, slug, url, icon, image, display_order, status, menu_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (menuItem.getParentId() != null) {
                pstmt.setInt(1, menuItem.getParentId());
            } else {
                pstmt.setNull(1, Types.INTEGER);
            }
            pstmt.setString(2, menuItem.getTitle());
            pstmt.setString(3, menuItem.getSlug());
            pstmt.setString(4, menuItem.getUrl());
            pstmt.setString(5, menuItem.getIcon());
            pstmt.setString(6, menuItem.getImage());
            pstmt.setInt(7, menuItem.getDisplayOrder());
            pstmt.setString(8, menuItem.getStatus());
            pstmt.setString(9, menuItem.getMenuType());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    menuItem.setMenuItemId(rs.getInt(1));
                    return findById(menuItem.getMenuItemId()).orElse(menuItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public MenuItem update(MenuItem menuItem) {
        String sql = "UPDATE menu_items SET parent_id = ?, title = ?, slug = ?, url = ?, icon = ?, image = ?, display_order = ?, status = ?, menu_type = ? WHERE menu_item_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (menuItem.getParentId() != null) {
                pstmt.setInt(1, menuItem.getParentId());
            } else {
                pstmt.setNull(1, Types.INTEGER);
            }
            pstmt.setString(2, menuItem.getTitle());
            pstmt.setString(3, menuItem.getSlug());
            pstmt.setString(4, menuItem.getUrl());
            pstmt.setString(5, menuItem.getIcon());
            pstmt.setString(6, menuItem.getImage());
            pstmt.setInt(7, menuItem.getDisplayOrder());
            pstmt.setString(8, menuItem.getStatus());
            pstmt.setString(9, menuItem.getMenuType());
            pstmt.setInt(10, menuItem.getMenuItemId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return findById(menuItem.getMenuItemId()).orElse(menuItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM menu_items WHERE menu_item_id = ?";
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

    private MenuItem extractMenuItem(ResultSet rs) throws SQLException {
        MenuItem menuItem = new MenuItem();
        menuItem.setMenuItemId(rs.getInt("menu_item_id"));
        int parentId = rs.getInt("parent_id");
        if (!rs.wasNull()) {
            menuItem.setParentId(parentId);
        }
        menuItem.setTitle(rs.getString("title"));
        menuItem.setSlug(rs.getString("slug"));
        menuItem.setUrl(rs.getString("url"));
        menuItem.setIcon(rs.getString("icon"));
        menuItem.setImage(rs.getString("image"));
        menuItem.setDisplayOrder(rs.getInt("display_order"));
        menuItem.setStatus(rs.getString("status"));
        menuItem.setMenuType(rs.getString("menu_type"));
        menuItem.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        menuItem.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        return menuItem;
    }
}
