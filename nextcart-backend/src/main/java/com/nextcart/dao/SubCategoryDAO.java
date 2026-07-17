package com.nextcart.dao;

import com.nextcart.model.SubCategory;
import java.util.List;
import java.util.Optional;

public interface SubCategoryDAO {
    Optional<SubCategory> findById(Integer subCategoryId);
    Optional<SubCategory> findBySlug(String slug);
    List<SubCategory> findAll();
    List<SubCategory> findByCategoryId(Integer categoryId);
    SubCategory create(SubCategory subCategory);
    SubCategory update(SubCategory subCategory);
    boolean delete(Integer subCategoryId);
}
