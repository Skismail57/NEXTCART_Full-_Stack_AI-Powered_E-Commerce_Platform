'use client';

import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { Product, CartItem as BackendCartItem, ApiResponse } from '@/types';
import api from '@/lib/api';
import { useAuth } from './AuthContext';

interface CartItem {
  id: number;
  product: Product;
  quantity: number;
}

interface CartContextType {
  cartItems: CartItem[];
  addToCart: (product: Product, quantity?: number) => Promise<void>;
  updateQuantity: (id: number, quantity: number) => Promise<void>;
  removeFromCart: (id: number) => Promise<void>;
  clearCart: () => Promise<void>;
  totalItems: number;
  totalPrice: number;
  loading: boolean;
}

const CartContext = createContext<CartContextType | undefined>(undefined);

export function CartProvider({ children }: { children: ReactNode }) {
  const [cartItems, setCartItems] = useState<CartItem[]>([]);
  const [loading, setLoading] = useState(true);
  const { user } = useAuth();

  const fetchCart = async () => {
    if (!user) {
      setCartItems([]);
      setLoading(false);
      return;
    }
    try {
      const res = await api.get<ApiResponse<BackendCartItem[]>>('/cart');
      const items = res.data.data.map(item => ({
        id: item.cartItemId,
        product: item.product!,
        quantity: item.quantity
      }));
      setCartItems(items);
    } catch (err) {
      console.error('Failed to fetch cart', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCart();
  }, [user]);

  const addToCart = async (product: Product, quantity = 1) => {
    if (!user) return;
    try {
      const res = await api.post<ApiResponse<BackendCartItem>>('/cart/add', {
        productId: product.productId,
        variantId: null,
        quantity
      });
      await fetchCart();
    } catch (err) {
      console.error('Failed to add to cart', err);
    }
  };

  const updateQuantity = async (id: number, quantity: number) => {
    if (!user) return;
    if (quantity <= 0) {
      await removeFromCart(id);
      return;
    }
    try {
      await api.put<ApiResponse<BackendCartItem>>(`/cart/item/${id}`, { quantity });
      await fetchCart();
    } catch (err) {
      console.error('Failed to update quantity', err);
    }
  };

  const removeFromCart = async (id: number) => {
    if (!user) return;
    try {
      await api.delete(`/cart/item/${id}`);
      await fetchCart();
    } catch (err) {
      console.error('Failed to remove from cart', err);
    }
  };

  const clearCart = async () => {
    if (!user) return;
    try {
      await api.delete('/cart');
      await fetchCart();
    } catch (err) {
      console.error('Failed to clear cart', err);
    }
  };

  const totalItems = cartItems.reduce((sum, item) => sum + item.quantity, 0);
  const totalPrice = cartItems.reduce(
    (sum, item) => sum + (item.product.discountPrice || item.product.price) * item.quantity,
    0
  );

  return (
    <CartContext.Provider
      value={{
        cartItems,
        addToCart,
        updateQuantity,
        removeFromCart,
        clearCart,
        totalItems,
        totalPrice,
        loading
      }}
    >
      {children}
    </CartContext.Provider>
  );
}

export function useCart() {
  const context = useContext(CartContext);
  if (context === undefined) {
    throw new Error('useCart must be used within a CartProvider');
  }
  return context;
}
