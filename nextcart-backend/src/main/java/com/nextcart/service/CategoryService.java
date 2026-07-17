package com.nextcart.service;

import com.nextcart.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> getCategoryById(Integer id);
    Optional<Category> getCategoryBySlug(String slug);
    List<Category> getAllCategories();
    Category createCategory(Category category);
    Category updateCategory(Category category);
    void deleteCategory(Integer categoryId);
}
