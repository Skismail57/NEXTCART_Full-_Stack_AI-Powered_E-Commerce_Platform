package com.nextcart.serviceImpl;

import com.nextcart.dao.CmsPageDAO;
import com.nextcart.daoImpl.CmsPageDAOImpl;
import com.nextcart.model.CmsPage;
import com.nextcart.service.CmsPageService;
import java.util.List;
import java.util.Optional;

public class CmsPageServiceImpl implements CmsPageService {
    private final CmsPageDAO cmsPageDAO = new CmsPageDAOImpl();

    @Override
    public Optional<CmsPage> getCmsPageById(Integer id) {
        return cmsPageDAO.findById(id);
    }

    @Override
    public Optional<CmsPage> getCmsPageBySlug(String slug) {
        return cmsPageDAO.findBySlug(slug);
    }

    @Override
    public List<CmsPage> getAllCmsPages() {
        return cmsPageDAO.findAll();
    }

    @Override
    public CmsPage createCmsPage(CmsPage cmsPage) {
        return cmsPageDAO.create(cmsPage);
    }

    @Override
    public CmsPage updateCmsPage(CmsPage cmsPage) {
        return cmsPageDAO.update(cmsPage);
    }

    @Override
    public void deleteCmsPage(Integer id) {
        cmsPageDAO.delete(id);
    }
}
