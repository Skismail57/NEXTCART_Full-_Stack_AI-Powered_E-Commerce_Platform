package com.nextcart.model;

import java.time.LocalDateTime;

public class Inventory {
    private Integer inventoryId;
    private Integer productId;
    private String warehouse;
    private Integer availableStock;
    private Integer reservedStock;
    private Integer damagedStock;
    private LocalDateTime updatedAt;

    public Inventory() {
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }

    public Integer getReservedStock() {
        return reservedStock;
    }

    public void setReservedStock(Integer reservedStock) {
        this.reservedStock = reservedStock;
    }

    public Integer getDamagedStock() {
        return damagedStock;
    }

    public void setDamagedStock(Integer damagedStock) {
        this.damagedStock = damagedStock;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
