package com.nextcart.dao;

import com.nextcart.model.HomepageSectionCategory;
import java.util.List;

public interface HomepageSectionCategoryDAO {
    List<HomepageSectionCategory> findBySectionId(Integer sectionId);
    HomepageSectionCategory create(HomepageSectionCategory sc);
    void deleteBySectionId(Integer sectionId);
}
