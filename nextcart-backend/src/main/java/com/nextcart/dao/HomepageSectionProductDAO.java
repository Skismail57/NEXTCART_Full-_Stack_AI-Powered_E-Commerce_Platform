package com.nextcart.dao;

import com.nextcart.model.HomepageSectionProduct;
import java.util.List;

public interface HomepageSectionProductDAO {
    List<HomepageSectionProduct> findBySectionId(Integer sectionId);
    HomepageSectionProduct create(HomepageSectionProduct sp);
    void deleteBySectionId(Integer sectionId);
}
