package com.nextcart.dao;

import com.nextcart.model.HomepageBanner;
import java.util.List;
import java.util.Optional;

public interface HomepageBannerDAO {
    Optional<HomepageBanner> findById(Integer bannerId);
    List<HomepageBanner> findAll();
    List<HomepageBanner> findByPosition(String position);
    List<HomepageBanner> findActive();
    HomepageBanner create(HomepageBanner banner);
    HomepageBanner update(HomepageBanner banner);
    void delete(Integer bannerId);
}
