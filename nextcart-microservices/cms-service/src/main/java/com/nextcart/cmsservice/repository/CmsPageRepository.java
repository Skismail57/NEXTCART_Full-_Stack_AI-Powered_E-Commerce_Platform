package com.nextcart.cmsservice.repository;

import com.nextcart.cmsservice.model.CmsPage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CmsPageRepository extends JpaRepository<CmsPage, Integer> {
    Optional<CmsPage> findBySlug(String slug);
    List<CmsPage> findByStatus(CmsPage.CmsPageStatus status);
}
