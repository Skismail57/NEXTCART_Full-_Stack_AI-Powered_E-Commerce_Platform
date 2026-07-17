package com.nextcart.service;

import com.nextcart.model.Inventory;
import java.util.List;
import java.util.Optional;

public interface InventoryService {
    Optional<Inventory> getInventoryById(Integer id);
    Optional<Inventory> getInventoryByProductId(Integer productId);
    List<Inventory> getAllInventories();
    List<Inventory> getLowStockInventories(Integer threshold);
    Inventory createInventory(Inventory inventory);
    Inventory updateInventory(Inventory inventory);
    void deleteInventory(Integer id);
}
