package com.nextcart.dao;

import com.nextcart.model.ProductImage;
import java.util.List;

public interface ProductImageDAO {
    ProductImage create(ProductImage productImage);
    List<ProductImage> findByProductId(Integer productId);
    void deleteByProductId(Integer productId);
}
