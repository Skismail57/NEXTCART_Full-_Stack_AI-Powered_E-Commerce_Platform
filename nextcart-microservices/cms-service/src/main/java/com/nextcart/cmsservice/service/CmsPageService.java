package com.nextcart.cmsservice.service;

import com.nextcart.cmsservice.model.CmsPage;

import java.util.List;
import java.util.Optional;

public interface CmsPageService {
    List<CmsPage> getAllCmsPages();
    Optional<CmsPage> getCmsPageById(Integer id);
    Optional<CmsPage> getCmsPageBySlug(String slug);
    List<CmsPage> getActiveCmsPages();
    CmsPage createCmsPage(CmsPage cmsPage);
    CmsPage updateCmsPage(Integer id, CmsPage cmsPage);
    void deleteCmsPage(Integer id);
}
