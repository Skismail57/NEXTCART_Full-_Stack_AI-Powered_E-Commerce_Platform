package com.nextcart.service;

import com.nextcart.model.HeroBanner;
import java.util.List;
import java.util.Optional;

public interface HeroBannerService {
    Optional<HeroBanner> getHeroBannerById(Integer id);
    List<HeroBanner> getAllHeroBanners();
    List<HeroBanner> getActiveHeroBanners();
    HeroBanner createHeroBanner(HeroBanner heroBanner);
    HeroBanner updateHeroBanner(HeroBanner heroBanner);
    void deleteHeroBanner(Integer heroId);
}
