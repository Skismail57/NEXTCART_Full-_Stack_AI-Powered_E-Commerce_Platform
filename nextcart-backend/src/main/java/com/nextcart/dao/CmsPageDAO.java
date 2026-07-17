package com.nextcart.dao;

import com.nextcart.model.CmsPage;
import java.util.List;
import java.util.Optional;

public interface CmsPageDAO {
    Optional<CmsPage> findById(Integer pageId);
    Optional<CmsPage> findBySlug(String slug);
    List<CmsPage> findAll();
    CmsPage create(CmsPage cmsPage);
    CmsPage update(CmsPage cmsPage);
    void delete(Integer pageId);
}
