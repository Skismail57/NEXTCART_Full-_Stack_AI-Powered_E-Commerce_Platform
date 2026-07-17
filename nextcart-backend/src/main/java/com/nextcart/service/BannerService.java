package com.nextcart.service;

import com.nextcart.model.Banner;
import java.util.List;
import java.util.Optional;

public interface BannerService {
    Optional<Banner> getBannerById(Integer id);
    List<Banner> getAllBanners();
    List<Banner> getActiveBanners();
    Banner createBanner(Banner banner);
    Banner updateBanner(Banner banner);
    void deleteBanner(Integer bannerId);
}
