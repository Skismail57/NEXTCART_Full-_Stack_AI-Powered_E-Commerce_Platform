package com.nextcart.dao;

import com.nextcart.model.HomepageSection;
import java.util.List;
import java.util.Optional;

public interface HomepageSectionDAO {
    Optional<HomepageSection> findById(Integer sectionId);
    List<HomepageSection> findAll();
    List<HomepageSection> findActive();
    HomepageSection create(HomepageSection section);
    HomepageSection update(HomepageSection section);
    void delete(Integer sectionId);
}
