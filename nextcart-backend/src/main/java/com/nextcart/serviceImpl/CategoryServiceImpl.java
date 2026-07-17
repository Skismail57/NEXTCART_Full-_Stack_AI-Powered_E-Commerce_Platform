package com.nextcart.serviceImpl;

import com.nextcart.dao.CategoryDAO;
import com.nextcart.daoImpl.CategoryDAOImpl;
import com.nextcart.model.Category;
import com.nextcart.service.CategoryService;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    public Optional<Category> getCategoryById(Integer id) {
        return categoryDAO.findById(id);
    }

    @Override
    public Optional<Category> getCategoryBySlug(String slug) {
        return categoryDAO.findBySlug(slug);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }

    @Override
    public Category createCategory(Category category) {
        return categoryDAO.create(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryDAO.update(category);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        categoryDAO.delete(categoryId);
    }
}
