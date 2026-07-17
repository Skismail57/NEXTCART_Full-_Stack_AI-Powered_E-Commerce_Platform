package com.nextcart.cmsservice.service.impl;

import com.nextcart.cmsservice.model.HomepageSection;
import com.nextcart.cmsservice.repository.HomepageSectionRepository;
import com.nextcart.cmsservice.service.HomepageSectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HomepageSectionServiceImpl implements HomepageSectionService {
    private final HomepageSectionRepository homepageSectionRepository;

    @Override
    public List<HomepageSection> getAllHomepageSections() {
        return homepageSectionRepository.findAll();
    }

    @Override
    public Optional<HomepageSection> getHomepageSectionById(Integer id) {
        return homepageSectionRepository.findById(id);
    }

    @Override
    public List<HomepageSection> getActiveHomepageSections() {
        return homepageSectionRepository.findByStatus(HomepageSection.HomepageSectionStatus.ACTIVE);
    }

    @Override
    public HomepageSection createHomepageSection(HomepageSection homepageSection) {
        return homepageSectionRepository.save(homepageSection);
    }

    @Override
    public HomepageSection updateHomepageSection(Integer id, HomepageSection homepageSection) {
        return homepageSectionRepository.findById(id)
                .map(existing -> {
                    existing.setSectionName(homepageSection.getSectionName());
                    existing.setSectionType(homepageSection.getSectionType());
                    existing.setTitle(homepageSection.getTitle());
                    existing.setSubtitle(homepageSection.getSubtitle());
                    existing.setLayout(homepageSection.getLayout());
                    existing.setDisplayOrder(homepageSection.getDisplayOrder());
                    existing.setStatus(homepageSection.getStatus());
                    existing.setConfig(homepageSection.getConfig());
                    return homepageSectionRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Homepage section not found"));
    }

    @Override
    public void deleteHomepageSection(Integer id) {
        homepageSectionRepository.deleteById(id);
    }
}
