package com.nextcart.cmsservice.service.impl;

import com.nextcart.cmsservice.model.HeroBanner;
import com.nextcart.cmsservice.repository.HeroBannerRepository;
import com.nextcart.cmsservice.service.HeroBannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeroBannerServiceImpl implements HeroBannerService {
    private final HeroBannerRepository heroBannerRepository;

    @Override
    public List<HeroBanner> getAllHeroBanners() {
        return heroBannerRepository.findAll();
    }

    @Override
    public Optional<HeroBanner> getHeroBannerById(Integer id) {
        return heroBannerRepository.findById(id);
    }

    @Override
    public List<HeroBanner> getActiveHeroBanners() {
        return heroBannerRepository.findByStatus(HeroBanner.HeroBannerStatus.ACTIVE);
    }

    @Override
    public HeroBanner createHeroBanner(HeroBanner heroBanner) {
        return heroBannerRepository.save(heroBanner);
    }

    @Override
    public HeroBanner updateHeroBanner(Integer id, HeroBanner heroBanner) {
        return heroBannerRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(heroBanner.getTitle());
                    existing.setSubtitle(heroBanner.getSubtitle());
                    existing.setImageUrl(heroBanner.getImageUrl());
                    existing.setButtonText(heroBanner.getButtonText());
                    existing.setButtonLink(heroBanner.getButtonLink());
                    existing.setDisplayOrder(heroBanner.getDisplayOrder());
                    existing.setStatus(heroBanner.getStatus());
                    return heroBannerRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Hero banner not found"));
    }

    @Override
    public void deleteHeroBanner(Integer id) {
        heroBannerRepository.deleteById(id);
    }
}
