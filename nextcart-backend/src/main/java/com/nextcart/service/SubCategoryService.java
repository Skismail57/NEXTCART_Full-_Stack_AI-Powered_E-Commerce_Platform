package com.nextcart.service;

import com.nextcart.model.SubCategory;

import java.util.List;
import java.util.Optional;

public interface SubCategoryService {
    Optional<SubCategory> getSubCategoryById(Integer id);
    Optional<SubCategory> getSubCategoryBySlug(String slug);
    List<SubCategory> getAllSubCategories();
    List<SubCategory> getSubCategoriesByCategoryId(Integer categoryId);
    SubCategory createSubCategory(SubCategory subCategory);
    SubCategory updateSubCategory(SubCategory subCategory);
    boolean deleteSubCategory(Integer id);
}
