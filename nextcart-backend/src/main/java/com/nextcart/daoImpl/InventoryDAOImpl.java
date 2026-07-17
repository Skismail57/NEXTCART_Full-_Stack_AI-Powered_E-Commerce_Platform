package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.InventoryDAO;
import com.nextcart.model.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryDAOImpl implements InventoryDAO {
    private static final Logger logger = LoggerFactory.getLogger(InventoryDAOImpl.class);

    @Override
    public Optional<Inventory> findById(Integer inventoryId) {
        String sql = "SELECT * FROM inventory WHERE inventory_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, inventoryId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractInventory(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding inventory by id: " + inventoryId, e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Inventory> findByProductId(Integer productId) {
        String sql = "SELECT * FROM inventory WHERE product_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractInventory(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding inventory by product id: " + productId, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Inventory> findAll() {
        List<Inventory> inventories = new ArrayList<>();
        String sql = "SELECT * FROM inventory ORDER BY product_id";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                inventories.add(extractInventory(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding all inventories", e);
        }

        return inventories;
    }

    @Override
    public List<Inventory> findLowStock(Integer threshold) {
        List<Inventory> inventories = new ArrayList<>();
        String sql = "SELECT * FROM inventory WHERE available_stock < ? ORDER BY available_stock";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, threshold);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                inventories.add(extractInventory(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding low stock", e);
        }

        return inventories;
    }

    @Override
    public Inventory create(Inventory inventory) {
        String sql = "INSERT INTO inventory (product_id, warehouse, available_stock, reserved_stock, damaged_stock) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, inventory.getProductId());
            stmt.setString(2, inventory.getWarehouse());
            stmt.setInt(3, inventory.getAvailableStock() != null ? inventory.getAvailableStock() : 0);
            stmt.setInt(4, inventory.getReservedStock() != null ? inventory.getReservedStock() : 0);
            stmt.setInt(5, inventory.getDamagedStock() != null ? inventory.getDamagedStock() : 0);
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                inventory.setInventoryId(rs.getInt(1));
            }
            
            return findById(inventory.getInventoryId()).orElse(inventory);
        } catch (SQLException e) {
            logger.error("Error creating inventory", e);
            return inventory;
        }
    }

    @Override
    public Inventory update(Inventory inventory) {
        String sql = "UPDATE inventory SET product_id = ?, warehouse = ?, available_stock = ?, reserved_stock = ?, damaged_stock = ? WHERE inventory_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, inventory.getProductId());
            stmt.setString(2, inventory.getWarehouse());
            stmt.setInt(3, inventory.getAvailableStock());
            stmt.setInt(4, inventory.getReservedStock());
            stmt.setInt(5, inventory.getDamagedStock());
            stmt.setInt(6, inventory.getInventoryId());
            
            stmt.executeUpdate();
            return findById(inventory.getInventoryId()).orElse(inventory);
        } catch (SQLException e) {
            logger.error("Error updating inventory", e);
            return inventory;
        }
    }

    @Override
    public void delete(Integer inventoryId) {
        String sql = "DELETE FROM inventory WHERE inventory_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, inventoryId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting inventory", e);
        }
    }

    private Inventory extractInventory(ResultSet rs) throws SQLException {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(rs.getInt("inventory_id"));
        inventory.setProductId(rs.getInt("product_id"));
        inventory.setWarehouse(rs.getString("warehouse"));
        inventory.setAvailableStock(rs.getInt("available_stock"));
        inventory.setReservedStock(rs.getInt("reserved_stock"));
        inventory.setDamagedStock(rs.getInt("damaged_stock"));

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            inventory.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return inventory;
    }
}
