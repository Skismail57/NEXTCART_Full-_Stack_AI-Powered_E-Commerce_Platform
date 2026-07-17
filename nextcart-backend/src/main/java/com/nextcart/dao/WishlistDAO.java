package com.nextcart.dao;

import com.nextcart.model.Wishlist;
import java.util.List;
import java.util.Optional;

public interface WishlistDAO {
    Optional<Wishlist> findById(Integer wishlistId);
    List<Wishlist> findByUserId(Integer userId);
    Optional<Wishlist> findByUserIdAndProductId(Integer userId, Integer productId);
    Wishlist create(Wishlist wishlist);
    boolean delete(Integer wishlistId);
    boolean deleteByUserIdAndProductId(Integer userId, Integer productId);
    boolean exists(Integer userId, Integer productId);
}
