package com.nextcart.cmsservice.repository;

import com.nextcart.cmsservice.model.HomepageSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HomepageSectionRepository extends JpaRepository<HomepageSection, Integer> {
    Optional<HomepageSection> findById(Integer id);
    List<HomepageSection> findByStatus(HomepageSection.HomepageSectionStatus status);
}
