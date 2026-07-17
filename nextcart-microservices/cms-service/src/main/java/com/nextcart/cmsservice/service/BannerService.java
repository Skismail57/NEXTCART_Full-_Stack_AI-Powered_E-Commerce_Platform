package com.nextcart.cmsservice.service;

import com.nextcart.cmsservice.model.Banner;

import java.util.List;
import java.util.Optional;

public interface BannerService {
    List<Banner> getAllBanners();
    Optional<Banner> getBannerById(Integer id);
    List<Banner> getActiveBanners();
    Banner createBanner(Banner banner);
    Banner updateBanner(Integer id, Banner banner);
    void deleteBanner(Integer id);
}
