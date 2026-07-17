package com.nextcart.serviceImpl;

import com.nextcart.dao.HomepageBannerDAO;
import com.nextcart.daoImpl.HomepageBannerDAOImpl;
import com.nextcart.model.HomepageBanner;
import com.nextcart.service.HomepageBannerService;
import java.util.List;
import java.util.Optional;

public class HomepageBannerServiceImpl implements HomepageBannerService {
    private final HomepageBannerDAO bannerDAO = new HomepageBannerDAOImpl();

    @Override
    public Optional<HomepageBanner> getBannerById(Integer bannerId) {
        return bannerDAO.findById(bannerId);
    }

    @Override
    public List<HomepageBanner> getAllBanners() {
        return bannerDAO.findAll();
    }

    @Override
    public List<HomepageBanner> getBannersByPosition(String position) {
        return bannerDAO.findByPosition(position);
    }

    @Override
    public List<HomepageBanner> getActiveBanners() {
        return bannerDAO.findActive();
    }

    @Override
    public HomepageBanner createBanner(HomepageBanner banner) {
        return bannerDAO.create(banner);
    }

    @Override
    public HomepageBanner updateBanner(HomepageBanner banner) {
        return bannerDAO.update(banner);
    }

    @Override
    public void deleteBanner(Integer bannerId) {
        bannerDAO.delete(bannerId);
    }
}
