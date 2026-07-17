package com.nextcart.dao;

import com.nextcart.model.CartItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CartItemDAO {
    Optional<CartItem> findById(Integer cartItemId);
    List<CartItem> findByCartId(Integer cartId);
    Optional<CartItem> findByCartIdAndProductIdAndVariantId(Integer cartId, Integer productId, Integer variantId);
    CartItem create(CartItem cartItem);
    CartItem update(CartItem cartItem);
    boolean delete(Integer cartItemId);
    boolean deleteByCartId(Integer cartId);
    BigDecimal getCartTotal(Integer cartId);
    int getCartItemCount(Integer cartId);
}
