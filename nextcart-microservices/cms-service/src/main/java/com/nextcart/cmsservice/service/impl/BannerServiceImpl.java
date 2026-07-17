package com.nextcart.cmsservice.service.impl;

import com.nextcart.cmsservice.model.Banner;
import com.nextcart.cmsservice.repository.BannerRepository;
import com.nextcart.cmsservice.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;

    @Override
    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    @Override
    public Optional<Banner> getBannerById(Integer id) {
        return bannerRepository.findById(id);
    }

    @Override
    public List<Banner> getActiveBanners() {
        return bannerRepository.findByStatus(Banner.BannerStatus.ACTIVE);
    }

    @Override
    public Banner createBanner(Banner banner) {
        return bannerRepository.save(banner);
    }

    @Override
    public Banner updateBanner(Integer id, Banner banner) {
        return bannerRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(banner.getTitle());
                    existing.setSubtitle(banner.getSubtitle());
                    existing.setImageUrl(banner.getImageUrl());
                    existing.setButtonText(banner.getButtonText());
                    existing.setButtonLink(banner.getButtonLink());
                    existing.setDisplayOrder(banner.getDisplayOrder());
                    existing.setStatus(banner.getStatus());
                    return bannerRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Banner not found"));
    }

    @Override
    public void deleteBanner(Integer id) {
        bannerRepository.deleteById(id);
    }
}
