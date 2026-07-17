package com.nextcart.serviceImpl;

import com.nextcart.dao.BannerDAO;
import com.nextcart.daoImpl.BannerDAOImpl;
import com.nextcart.model.Banner;
import com.nextcart.service.BannerService;

import java.util.List;
import java.util.Optional;

public class BannerServiceImpl implements BannerService {
    private final BannerDAO bannerDAO = new BannerDAOImpl();

    @Override
    public Optional<Banner> getBannerById(Integer id) {
        return bannerDAO.findById(id);
    }

    @Override
    public List<Banner> getAllBanners() {
        return bannerDAO.findAll();
    }

    @Override
    public List<Banner> getActiveBanners() {
        return bannerDAO.findActive();
    }

    @Override
    public Banner createBanner(Banner banner) {
        return bannerDAO.create(banner);
    }

    @Override
    public Banner updateBanner(Banner banner) {
        return bannerDAO.update(banner);
    }

    @Override
    public void deleteBanner(Integer bannerId) {
        bannerDAO.delete(bannerId);
    }
}
