package com.nextcart.serviceImpl;

import com.nextcart.dao.CartDAO;
import com.nextcart.dao.CartItemDAO;
import com.nextcart.dao.ProductDAO;
import com.nextcart.daoImpl.CartDAOImpl;
import com.nextcart.daoImpl.CartItemDAOImpl;
import com.nextcart.daoImpl.ProductDAOImpl;
import com.nextcart.model.Cart;
import com.nextcart.model.CartItem;
import com.nextcart.model.Product;
import com.nextcart.service.CartService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CartServiceImpl implements CartService {
    private final CartDAO cartDAO = new CartDAOImpl();
    private final CartItemDAO cartItemDAO = new CartItemDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();

    @Override
    public Optional<Cart> getCartByUserId(Integer userId) {
        return cartDAO.findByUserId(userId);
    }

    @Override
    public Cart getOrCreateCartByUserId(Integer userId) {
        return cartDAO.findOrCreateByUserId(userId);
    }

    @Override
    public List<CartItem> getCartItemsByCartId(Integer cartId) {
        return cartItemDAO.findByCartId(cartId);
    }

    @Override
    public CartItem addCartItem(Integer userId, Integer productId, Integer variantId, int quantity) {
        Cart cart = getOrCreateCartByUserId(userId);
        Optional<Product> productOpt = productDAO.findById(productId);

        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        Product product = productOpt.get();
        BigDecimal price = product.getDiscountPrice() != null ? product.getDiscountPrice() : product.getPrice();

        Optional<CartItem> existingItemOpt = cartItemDAO.findByCartIdAndProductIdAndVariantId(cart.getCartId(), productId, variantId);
        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setTotal(price.multiply(BigDecimal.valueOf(existingItem.getQuantity())));
            return cartItemDAO.update(existingItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCartId(cart.getCartId());
            cartItem.setProductId(productId);
            cartItem.setVariantId(variantId);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(price);
            cartItem.setTotal(price.multiply(BigDecimal.valueOf(quantity)));
            return cartItemDAO.create(cartItem);
        }
    }

    @Override
    public CartItem updateCartItemQuantity(Integer cartItemId, int quantity) {
        Optional<CartItem> cartItemOpt = cartItemDAO.findById(cartItemId);
        if (cartItemOpt.isEmpty()) {
            throw new IllegalArgumentException("Cart item not found");
        }

        CartItem cartItem = cartItemOpt.get();
        if (quantity <= 0) {
            removeCartItem(cartItemId);
            return null;
        }

        cartItem.setQuantity(quantity);
        cartItem.setTotal(cartItem.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return cartItemDAO.update(cartItem);
    }

    @Override
    public boolean removeCartItem(Integer cartItemId) {
        return cartItemDAO.delete(cartItemId);
    }

    @Override
    public boolean clearCart(Integer cartId) {
        return cartItemDAO.deleteByCartId(cartId);
    }

    @Override
    public BigDecimal getCartTotal(Integer cartId) {
        return cartItemDAO.getCartTotal(cartId);
    }

    @Override
    public int getCartItemCount(Integer cartId) {
        return cartItemDAO.getCartItemCount(cartId);
    }
}
