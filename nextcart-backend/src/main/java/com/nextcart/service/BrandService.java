package com.nextcart.service;

import com.nextcart.model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    Optional<Brand> getBrandById(Integer id);
    Optional<Brand> getBrandBySlug(String slug);
    List<Brand> getAllBrands();
    Brand createBrand(Brand brand);
    Brand updateBrand(Brand brand);
    void deleteBrand(Integer brandId);
}
