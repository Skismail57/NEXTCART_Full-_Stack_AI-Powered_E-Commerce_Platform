package com.nextcart.service;

import com.nextcart.model.FlashSale;
import java.util.List;
import java.util.Optional;

public interface FlashSaleService {
    Optional<FlashSale> getFlashSaleById(Integer flashSaleId);
    List<FlashSale> getAllFlashSales();
    List<FlashSale> getActiveFlashSales();
    FlashSale createFlashSale(FlashSale flashSale);
    FlashSale updateFlashSale(FlashSale flashSale);
    void deleteFlashSale(Integer flashSaleId);
}
