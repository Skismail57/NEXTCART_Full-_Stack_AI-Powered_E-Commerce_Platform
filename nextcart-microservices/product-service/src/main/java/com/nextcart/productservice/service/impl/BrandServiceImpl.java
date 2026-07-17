package com.nextcart.productservice.service.impl;

import com.nextcart.productservice.model.Brand;
import com.nextcart.productservice.repository.BrandRepository;
import com.nextcart.productservice.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Optional<Brand> getBrandById(Integer id) {
        return brandRepository.findById(id);
    }

    @Override
    public Optional<Brand> getBrandByName(String name) {
        return brandRepository.findByName(name);
    }

    @Override
    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(Integer id, Brand brand) {
        return brandRepository.findById(id)
                .map(existingBrand -> {
                    existingBrand.setName(brand.getName());
                    existingBrand.setLogo(brand.getLogo());
                    existingBrand.setDescription(brand.getDescription());
                    existingBrand.setStatus(brand.getStatus());
                    return brandRepository.save(existingBrand);
                })
                .orElseThrow(() -> new RuntimeException("Brand not found"));
    }

    @Override
    public void deleteBrand(Integer id) {
        brandRepository.deleteById(id);
    }
}
