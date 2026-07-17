package com.nextcart.service;

import com.nextcart.model.HomepageBanner;
import java.util.List;
import java.util.Optional;

public interface HomepageBannerService {
    Optional<HomepageBanner> getBannerById(Integer bannerId);
    List<HomepageBanner> getAllBanners();
    List<HomepageBanner> getBannersByPosition(String position);
    List<HomepageBanner> getActiveBanners();
    HomepageBanner createBanner(HomepageBanner banner);
    HomepageBanner updateBanner(HomepageBanner banner);
    void deleteBanner(Integer bannerId);
}
