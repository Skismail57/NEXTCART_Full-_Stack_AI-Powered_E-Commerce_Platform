package com.nextcart.serviceImpl;

import com.nextcart.dao.BrandDAO;
import com.nextcart.daoImpl.BrandDAOImpl;
import com.nextcart.model.Brand;
import com.nextcart.service.BrandService;

import java.util.List;
import java.util.Optional;

public class BrandServiceImpl implements BrandService {
    private final BrandDAO brandDAO = new BrandDAOImpl();

    @Override
    public Optional<Brand> getBrandById(Integer id) {
        return brandDAO.findById(id);
    }

    @Override
    public Optional<Brand> getBrandBySlug(String slug) {
        return brandDAO.findBySlug(slug);
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandDAO.findAll();
    }

    @Override
    public Brand createBrand(Brand brand) {
        return brandDAO.create(brand);
    }

    @Override
    public Brand updateBrand(Brand brand) {
        return brandDAO.update(brand);
    }

    @Override
    public void deleteBrand(Integer brandId) {
        brandDAO.delete(brandId);
    }
}
