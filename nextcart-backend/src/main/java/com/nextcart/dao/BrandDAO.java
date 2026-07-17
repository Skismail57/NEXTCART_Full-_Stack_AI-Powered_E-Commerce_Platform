package com.nextcart.dao;

import com.nextcart.model.Brand;
import java.util.List;
import java.util.Optional;

public interface BrandDAO {
    Optional<Brand> findById(Integer brandId);
    Optional<Brand> findBySlug(String slug);
    List<Brand> findAll();
    Brand create(Brand brand);
    Brand update(Brand brand);
    void delete(Integer brandId);
}
