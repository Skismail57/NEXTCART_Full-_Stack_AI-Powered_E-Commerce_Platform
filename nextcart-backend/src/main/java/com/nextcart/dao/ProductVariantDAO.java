package com.nextcart.dao;

import com.nextcart.model.ProductVariant;
import java.util.List;

public interface ProductVariantDAO {
    ProductVariant create(ProductVariant productVariant);
    List<ProductVariant> findByProductId(Integer productId);
    void deleteByProductId(Integer productId);
}
