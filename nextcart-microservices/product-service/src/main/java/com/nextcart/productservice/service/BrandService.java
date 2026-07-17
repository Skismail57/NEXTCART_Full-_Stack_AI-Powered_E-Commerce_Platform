package com.nextcart.productservice.service;

import com.nextcart.productservice.model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    List<Brand> getAllBrands();
    Optional<Brand> getBrandById(Integer id);
    Optional<Brand> getBrandByName(String name);
    Brand createBrand(Brand brand);
    Brand updateBrand(Integer id, Brand brand);
    void deleteBrand(Integer id);
}
