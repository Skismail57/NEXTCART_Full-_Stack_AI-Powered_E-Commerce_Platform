package com.nextcart.cmsservice.service.impl;

import com.nextcart.cmsservice.model.CmsPage;
import com.nextcart.cmsservice.repository.CmsPageRepository;
import com.nextcart.cmsservice.service.CmsPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CmsPageServiceImpl implements CmsPageService {
    private final CmsPageRepository cmsPageRepository;

    @Override
    public List<CmsPage> getAllCmsPages() {
        return cmsPageRepository.findAll();
    }

    @Override
    public Optional<CmsPage> getCmsPageById(Integer id) {
        return cmsPageRepository.findById(id);
    }

    @Override
    public Optional<CmsPage> getCmsPageBySlug(String slug) {
        return cmsPageRepository.findBySlug(slug);
    }

    @Override
    public List<CmsPage> getActiveCmsPages() {
        return cmsPageRepository.findByStatus(CmsPage.CmsPageStatus.ACTIVE);
    }

    @Override
    public CmsPage createCmsPage(CmsPage cmsPage) {
        return cmsPageRepository.save(cmsPage);
    }

    @Override
    public CmsPage updateCmsPage(Integer id, CmsPage cmsPage) {
        return cmsPageRepository.findById(id)
                .map(existing -> {
                    existing.setSlug(cmsPage.getSlug());
                    existing.setTitle(cmsPage.getTitle());
                    existing.setContent(cmsPage.getContent());
                    existing.setMetaTitle(cmsPage.getMetaTitle());
                    existing.setMetaDescription(cmsPage.getMetaDescription());
                    existing.setMetaKeywords(cmsPage.getMetaKeywords());
                    existing.setStatus(cmsPage.getStatus());
                    return cmsPageRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("CMS page not found"));
    }

    @Override
    public void deleteCmsPage(Integer id) {
        cmsPageRepository.deleteById(id);
    }
}
