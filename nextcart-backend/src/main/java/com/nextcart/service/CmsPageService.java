package com.nextcart.service;

import com.nextcart.model.CmsPage;
import java.util.List;
import java.util.Optional;

public interface CmsPageService {
    Optional<CmsPage> getCmsPageById(Integer id);
    Optional<CmsPage> getCmsPageBySlug(String slug);
    List<CmsPage> getAllCmsPages();
    CmsPage createCmsPage(CmsPage cmsPage);
    CmsPage updateCmsPage(CmsPage cmsPage);
    void deleteCmsPage(Integer id);
}
