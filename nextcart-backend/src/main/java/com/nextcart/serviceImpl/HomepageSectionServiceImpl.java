package com.nextcart.serviceImpl;

import com.nextcart.dao.*;
import com.nextcart.daoImpl.*;
import com.nextcart.model.*;
import com.nextcart.service.HomepageSectionService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HomepageSectionServiceImpl implements HomepageSectionService {
    private final HomepageSectionDAO sectionDAO = new HomepageSectionDAOImpl();
    private final HomepageSectionProductDAO sectionProductDAO = new HomepageSectionProductDAOImpl();
    private final HomepageSectionCategoryDAO sectionCategoryDAO = new HomepageSectionCategoryDAOImpl();
    private final HomepageSectionBrandDAO sectionBrandDAO = new HomepageSectionBrandDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();
    private final CategoryDAO categoryDAO = new CategoryDAOImpl();
    private final BrandDAO brandDAO = new BrandDAOImpl();

    @Override
    public Optional<HomepageSection> getSectionById(Integer sectionId) {
        Optional<HomepageSection> sectionOpt = sectionDAO.findById(sectionId);
        if (sectionOpt.isPresent()) {
            populateSectionData(sectionOpt.get());
        }
        return sectionOpt;
    }

    @Override
    public List<HomepageSection> getAllSections() {
        List<HomepageSection> sections = sectionDAO.findAll();
        for (HomepageSection section : sections) {
            populateSectionData(section);
        }
        return sections;
    }

    @Override
    public List<HomepageSection> getActiveSections() {
        List<HomepageSection> sections = sectionDAO.findActive();
        for (HomepageSection section : sections) {
            populateSectionData(section);
        }
        return sections;
    }

    @Override
    public HomepageSection createSection(HomepageSection section) {
        return sectionDAO.create(section);
    }

    @Override
    public HomepageSection updateSection(HomepageSection section) {
        return sectionDAO.update(section);
    }

    @Override
    public void deleteSection(Integer sectionId) {
        sectionProductDAO.deleteBySectionId(sectionId);
        sectionCategoryDAO.deleteBySectionId(sectionId);
        sectionBrandDAO.deleteBySectionId(sectionId);
        sectionDAO.delete(sectionId);
    }

    private void populateSectionData(HomepageSection section) {
        String type = section.getSectionType();
        if ("PRODUCTS".equals(type)) {
            List<HomepageSectionProduct> spList = sectionProductDAO.findBySectionId(section.getSectionId());
            List<Product> products = new ArrayList<>();
            for (HomepageSectionProduct sp : spList) {
                Optional<Product> productOpt = productDAO.findById(sp.getProductId());
                productOpt.ifPresent(products::add);
            }
            section.setProducts(products);
        } else if ("CATEGORIES".equals(type)) {
            List<HomepageSectionCategory> scList = sectionCategoryDAO.findBySectionId(section.getSectionId());
            List<Category> categories = new ArrayList<>();
            for (HomepageSectionCategory sc : scList) {
                Optional<Category> categoryOpt = categoryDAO.findById(sc.getCategoryId());
                categoryOpt.ifPresent(categories::add);
            }
            section.setCategories(categories);
        } else if ("BRANDS".equals(type)) {
            List<HomepageSectionBrand> sbList = sectionBrandDAO.findBySectionId(section.getSectionId());
            List<Brand> brands = new ArrayList<>();
            for (HomepageSectionBrand sb : sbList) {
                Optional<Brand> brandOpt = brandDAO.findById(sb.getBrandId());
                brandOpt.ifPresent(brands::add);
            }
            section.setBrands(brands);
        }
    }
}
