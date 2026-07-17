package com.nextcart.dao;

import com.nextcart.model.FlashSaleProduct;
import java.util.List;

public interface FlashSaleProductDAO {
    List<FlashSaleProduct> findByFlashSaleId(Integer flashSaleId);
    FlashSaleProduct create(FlashSaleProduct flashSaleProduct);
    void deleteByFlashSaleId(Integer flashSaleId);
}
