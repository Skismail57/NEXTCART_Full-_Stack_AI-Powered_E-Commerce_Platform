package com.nextcart.cmsservice.repository;

import com.nextcart.cmsservice.model.HeroBanner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeroBannerRepository extends JpaRepository<HeroBanner, Integer> {
    Optional<HeroBanner> findById(Integer id);
    List<HeroBanner> findByStatus(HeroBanner.HeroBannerStatus status);
}
