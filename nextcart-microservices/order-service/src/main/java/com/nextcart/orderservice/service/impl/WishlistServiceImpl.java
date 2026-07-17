package com.nextcart.orderservice.service.impl;

import com.nextcart.orderservice.model.Wishlist;
import com.nextcart.orderservice.repository.WishlistRepository;
import com.nextcart.orderservice.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;

    @Override
    public List<Wishlist> getWishlistByUserId(Integer userId) {
        return wishlistRepository.findByUserId(userId);
    }

    @Override
    public Optional<Wishlist> addToWishlist(Integer userId, Integer productId) {
        Optional<Wishlist> existing = wishlistRepository.findByUserIdAndProductId(userId, productId);
        if (existing.isPresent()) {
            return existing;
        }
        Wishlist wishlist = new Wishlist();
        wishlist.setUserId(userId);
        wishlist.setProductId(productId);
        return Optional.of(wishlistRepository.save(wishlist));
    }

    @Override
    public void removeFromWishlist(Integer userId, Integer productId) {
        wishlistRepository.deleteByUserIdAndProductId(userId, productId);
    }
}
