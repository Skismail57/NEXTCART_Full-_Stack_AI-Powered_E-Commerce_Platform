package com.nextcart.dao;

import com.nextcart.model.HeroBanner;
import java.util.List;
import java.util.Optional;

public interface HeroBannerDAO {
    Optional<HeroBanner> findById(Integer heroId);
    List<HeroBanner> findAll();
    List<HeroBanner> findActive();
    HeroBanner create(HeroBanner heroBanner);
    HeroBanner update(HeroBanner heroBanner);
    void delete(Integer heroId);
}
