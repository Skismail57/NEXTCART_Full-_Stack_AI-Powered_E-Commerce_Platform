package com.nextcart.service;

import com.nextcart.model.Wishlist;

import java.util.List;
import java.util.Optional;

public interface WishlistService {
    Optional<Wishlist> getWishlistItemById(Integer id);
    List<Wishlist> getWishlistByUserId(Integer userId);
    Optional<Wishlist> getWishlistItemByUserIdAndProductId(Integer userId, Integer productId);
    Wishlist addToWishlist(Wishlist wishlist);
    boolean removeFromWishlist(Integer id);
    boolean removeFromWishlistByUserIdAndProductId(Integer userId, Integer productId);
    boolean isInWishlist(Integer userId, Integer productId);
}
