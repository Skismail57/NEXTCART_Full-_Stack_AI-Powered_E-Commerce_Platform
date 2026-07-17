package com.nextcart.serviceImpl;

import com.nextcart.dao.WishlistDAO;
import com.nextcart.daoImpl.WishlistDAOImpl;
import com.nextcart.model.Wishlist;
import com.nextcart.service.WishlistService;

import java.util.List;
import java.util.Optional;

public class WishlistServiceImpl implements WishlistService {
    private final WishlistDAO wishlistDAO = new WishlistDAOImpl();

    @Override
    public Optional<Wishlist> getWishlistItemById(Integer id) {
        return wishlistDAO.findById(id);
    }

    @Override
    public List<Wishlist> getWishlistByUserId(Integer userId) {
        return wishlistDAO.findByUserId(userId);
    }

    @Override
    public Optional<Wishlist> getWishlistItemByUserIdAndProductId(Integer userId, Integer productId) {
        return wishlistDAO.findByUserIdAndProductId(userId, productId);
    }

    @Override
    public Wishlist addToWishlist(Wishlist wishlist) {
        return wishlistDAO.create(wishlist);
    }

    @Override
    public boolean removeFromWishlist(Integer id) {
        return wishlistDAO.delete(id);
    }

    @Override
    public boolean removeFromWishlistByUserIdAndProductId(Integer userId, Integer productId) {
        return wishlistDAO.deleteByUserIdAndProductId(userId, productId);
    }

    @Override
    public boolean isInWishlist(Integer userId, Integer productId) {
        return wishlistDAO.exists(userId, productId);
    }
}
