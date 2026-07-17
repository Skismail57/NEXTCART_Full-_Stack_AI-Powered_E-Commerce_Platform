package com.nextcart.dao;

import com.nextcart.model.Cart;
import java.util.Optional;

public interface CartDAO {
    Optional<Cart> findById(Integer cartId);
    Optional<Cart> findByUserId(Integer userId);
    Cart create(Cart cart);
    Cart findOrCreateByUserId(Integer userId);
}
