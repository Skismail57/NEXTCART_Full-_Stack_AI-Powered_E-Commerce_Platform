package com.nextcart.serviceImpl;

import com.nextcart.dao.InventoryDAO;
import com.nextcart.daoImpl.InventoryDAOImpl;
import com.nextcart.model.Inventory;
import com.nextcart.service.InventoryService;
import java.util.List;
import java.util.Optional;

public class InventoryServiceImpl implements InventoryService {
    private final InventoryDAO inventoryDAO = new InventoryDAOImpl();

    @Override
    public Optional<Inventory> getInventoryById(Integer id) {
        return inventoryDAO.findById(id);
    }

    @Override
    public Optional<Inventory> getInventoryByProductId(Integer productId) {
        return inventoryDAO.findByProductId(productId);
    }

    @Override
    public List<Inventory> getAllInventories() {
        return inventoryDAO.findAll();
    }

    @Override
    public List<Inventory> getLowStockInventories(Integer threshold) {
        return inventoryDAO.findLowStock(threshold);
    }

    @Override
    public Inventory createInventory(Inventory inventory) {
        return inventoryDAO.create(inventory);
    }

    @Override
    public Inventory updateInventory(Inventory inventory) {
        return inventoryDAO.update(inventory);
    }

    @Override
    public void deleteInventory(Integer id) {
        inventoryDAO.delete(id);
    }
}
