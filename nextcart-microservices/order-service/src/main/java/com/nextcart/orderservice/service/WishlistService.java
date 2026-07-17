package com.nextcart.orderservice.service;

import com.nextcart.orderservice.model.Wishlist;

import java.util.List;
import java.util.Optional;

public interface WishlistService {
    List<Wishlist> getWishlistByUserId(Integer userId);
    Optional<Wishlist> addToWishlist(Integer userId, Integer productId);
    void removeFromWishlist(Integer userId, Integer productId);
}
