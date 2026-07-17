package com.nextcart.cmsservice.service;

import com.nextcart.cmsservice.model.HeroBanner;

import java.util.List;
import java.util.Optional;

public interface HeroBannerService {
    List<HeroBanner> getAllHeroBanners();
    Optional<HeroBanner> getHeroBannerById(Integer id);
    List<HeroBanner> getActiveHeroBanners();
    HeroBanner createHeroBanner(HeroBanner heroBanner);
    HeroBanner updateHeroBanner(Integer id, HeroBanner heroBanner);
    void deleteHeroBanner(Integer id);
}
