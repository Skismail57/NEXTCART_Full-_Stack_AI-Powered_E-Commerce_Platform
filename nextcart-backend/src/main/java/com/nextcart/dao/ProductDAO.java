package com.nextcart.dao;

import com.nextcart.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    Optional<Product> findById(Integer productId);
    Optional<Product> findBySlug(String slug);
    List<Product> findAll(int page, int size);
    List<Product> findFeatured(int limit);
    List<Product> findByCategory(Integer categoryId, int page, int size);
    List<Product> search(String query, int page, int size);
    Product create(Product product);
    Product update(Product product);
    void delete(Integer productId);
}
