package com.nextcart.dao;

import com.nextcart.model.HomepageSectionBrand;
import java.util.List;

public interface HomepageSectionBrandDAO {
    List<HomepageSectionBrand> findBySectionId(Integer sectionId);
    HomepageSectionBrand create(HomepageSectionBrand sb);
    void deleteBySectionId(Integer sectionId);
}
