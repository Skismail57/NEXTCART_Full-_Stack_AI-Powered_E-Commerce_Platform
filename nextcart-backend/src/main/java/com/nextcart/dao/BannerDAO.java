package com.nextcart.dao;

import com.nextcart.model.Banner;
import java.util.List;
import java.util.Optional;

public interface BannerDAO {
    Optional<Banner> findById(Integer bannerId);
    List<Banner> findAll();
    List<Banner> findActive();
    Banner create(Banner banner);
    Banner update(Banner banner);
    void delete(Integer bannerId);
}
