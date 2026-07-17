package com.nextcart.service;

import com.nextcart.model.HomepageSection;
import java.util.List;
import java.util.Optional;

public interface HomepageSectionService {
    Optional<HomepageSection> getSectionById(Integer sectionId);
    List<HomepageSection> getAllSections();
    List<HomepageSection> getActiveSections();
    HomepageSection createSection(HomepageSection section);
    HomepageSection updateSection(HomepageSection section);
    void deleteSection(Integer sectionId);
}
