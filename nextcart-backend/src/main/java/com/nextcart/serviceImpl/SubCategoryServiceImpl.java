package com.nextcart.serviceImpl;

import com.nextcart.dao.SubCategoryDAO;
import com.nextcart.daoImpl.SubCategoryDAOImpl;
import com.nextcart.model.SubCategory;
import com.nextcart.service.SubCategoryService;

import java.util.List;
import java.util.Optional;

public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryDAO subCategoryDAO = new SubCategoryDAOImpl();

    @Override
    public Optional<SubCategory> getSubCategoryById(Integer id) {
        return subCategoryDAO.findById(id);
    }

    @Override
    public Optional<SubCategory> getSubCategoryBySlug(String slug) {
        return subCategoryDAO.findBySlug(slug);
    }

    @Override
    public List<SubCategory> getAllSubCategories() {
        return subCategoryDAO.findAll();
    }

    @Override
    public List<SubCategory> getSubCategoriesByCategoryId(Integer categoryId) {
        return subCategoryDAO.findByCategoryId(categoryId);
    }

    @Override
    public SubCategory createSubCategory(SubCategory subCategory) {
        return subCategoryDAO.create(subCategory);
    }

    @Override
    public SubCategory updateSubCategory(SubCategory subCategory) {
        return subCategoryDAO.update(subCategory);
    }

    @Override
    public boolean deleteSubCategory(Integer id) {
        return subCategoryDAO.delete(id);
    }
}
