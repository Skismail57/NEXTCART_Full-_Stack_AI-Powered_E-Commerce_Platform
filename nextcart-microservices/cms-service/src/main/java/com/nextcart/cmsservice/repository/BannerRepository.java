package com.nextcart.cmsservice.repository;

import com.nextcart.cmsservice.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BannerRepository extends JpaRepository<Banner, Integer> {
    Optional<Banner> findById(Integer id);
    List<Banner> findByStatus(Banner.BannerStatus status);
}
