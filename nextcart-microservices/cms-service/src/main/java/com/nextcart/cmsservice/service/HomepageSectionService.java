package com.nextcart.cmsservice.service;

import com.nextcart.cmsservice.model.HomepageSection;

import java.util.List;
import java.util.Optional;

public interface HomepageSectionService {
    List<HomepageSection> getAllHomepageSections();
    Optional<HomepageSection> getHomepageSectionById(Integer id);
    List<HomepageSection> getActiveHomepageSections();
    HomepageSection createHomepageSection(HomepageSection homepageSection);
    HomepageSection updateHomepageSection(Integer id, HomepageSection homepageSection);
    void deleteHomepageSection(Integer id);
}
