package com.nextcart.service;

import com.nextcart.model.Cart;
import com.nextcart.model.CartItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CartService {
    Optional<Cart> getCartByUserId(Integer userId);
    Cart getOrCreateCartByUserId(Integer userId);
    List<CartItem> getCartItemsByCartId(Integer cartId);
    CartItem addCartItem(Integer userId, Integer productId, Integer variantId, int quantity);
    CartItem updateCartItemQuantity(Integer cartItemId, int quantity);
    boolean removeCartItem(Integer cartItemId);
    boolean clearCart(Integer cartId);
    BigDecimal getCartTotal(Integer cartId);
    int getCartItemCount(Integer cartId);
}
