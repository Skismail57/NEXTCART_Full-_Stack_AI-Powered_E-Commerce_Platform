package com.nextcart.dao;

import com.nextcart.model.FlashSale;
import java.util.List;
import java.util.Optional;

public interface FlashSaleDAO {
    Optional<FlashSale> findById(Integer flashSaleId);
    List<FlashSale> findAll();
    List<FlashSale> findActive();
    FlashSale create(FlashSale flashSale);
    FlashSale update(FlashSale flashSale);
    void delete(Integer flashSaleId);
}
