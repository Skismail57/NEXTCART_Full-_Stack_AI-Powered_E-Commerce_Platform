package com.nextcart.dao;

import com.nextcart.model.Inventory;
import java.util.List;
import java.util.Optional;

public interface InventoryDAO {
    Optional<Inventory> findById(Integer inventoryId);
    Optional<Inventory> findByProductId(Integer productId);
    List<Inventory> findAll();
    List<Inventory> findLowStock(Integer threshold);
    Inventory create(Inventory inventory);
    Inventory update(Inventory inventory);
    void delete(Integer inventoryId);
}
