package com.nextcart.model;

public class FlashSaleProduct {
    private Integer id;
    private Integer flashSaleId;
    private Integer productId;
    private Integer displayOrder;

    public FlashSaleProduct() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlashSaleId() {
        return flashSaleId;
    }

    public void setFlashSaleId(Integer flashSaleId) {
        this.flashSaleId = flashSaleId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
