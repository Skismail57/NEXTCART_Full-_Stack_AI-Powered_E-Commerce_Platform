package com.nextcart.serviceImpl;

import com.nextcart.dao.HeroBannerDAO;
import com.nextcart.daoImpl.HeroBannerDAOImpl;
import com.nextcart.model.HeroBanner;
import com.nextcart.service.HeroBannerService;

import java.util.List;
import java.util.Optional;

public class HeroBannerServiceImpl implements HeroBannerService {
    private final HeroBannerDAO heroBannerDAO = new HeroBannerDAOImpl();

    @Override
    public Optional<HeroBanner> getHeroBannerById(Integer id) {
        return heroBannerDAO.findById(id);
    }

    @Override
    public List<HeroBanner> getAllHeroBanners() {
        return heroBannerDAO.findAll();
    }

    @Override
    public List<HeroBanner> getActiveHeroBanners() {
        return heroBannerDAO.findActive();
    }

    @Override
    public HeroBanner createHeroBanner(HeroBanner heroBanner) {
        return heroBannerDAO.create(heroBanner);
    }

    @Override
    public HeroBanner updateHeroBanner(HeroBanner heroBanner) {
        return heroBannerDAO.update(heroBanner);
    }

    @Override
    public void deleteHeroBanner(Integer heroId) {
        heroBannerDAO.delete(heroId);
    }
}
