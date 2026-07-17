package com.nextcart.dao;

import com.nextcart.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryDAO {
    Optional<Category> findById(Integer categoryId);
    Optional<Category> findBySlug(String slug);
    List<Category> findAll();
    Category create(Category category);
    Category update(Category category);
    void delete(Integer categoryId);
}
