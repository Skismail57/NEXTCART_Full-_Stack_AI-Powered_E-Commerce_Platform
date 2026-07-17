'use client';

import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { Product, WishlistItem as BackendWishlistItem, ApiResponse } from '@/types';
import api from '@/lib/api';
import { useAuth } from './AuthContext';

interface WishlistItem {
  id: number;
  product: Product;
}

interface WishlistContextType {
  wishlistItems: WishlistItem[];
  addToWishlist: (product: Product) => Promise<void>;
  removeFromWishlist: (productId: number) => Promise<void>;
  isInWishlist: (productId: number) => boolean;
  clearWishlist: () => void;
  totalItems: number;
  loading: boolean;
}

const WishlistContext = createContext<WishlistContextType | undefined>(undefined);

export function WishlistProvider({ children }: { children: ReactNode }) {
  const [wishlistItems, setWishlistItems] = useState<WishlistItem[]>([]);
  const [loading, setLoading] = useState(true);
  const { user } = useAuth();

  const fetchWishlist = async () => {
    if (!user) {
      setWishlistItems([]);
      setLoading(false);
      return;
    }
    try {
      const res = await api.get<ApiResponse<BackendWishlistItem[]>>('/wishlist');
      const items = res.data.data.map(item => ({
        id: item.wishlistId,
        product: item.product!
      }));
      setWishlistItems(items);
    } catch (err) {
      console.error('Failed to fetch wishlist', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchWishlist();
  }, [user]);

  const addToWishlist = async (product: Product) => {
    if (!user) return;
    try {
      await api.post<ApiResponse<BackendWishlistItem>>('/wishlist', {
        productId: product.productId
      });
      await fetchWishlist();
    } catch (err) {
      console.error('Failed to add to wishlist', err);
    }
  };

  const removeFromWishlist = async (productId: number) => {
    if (!user) return;
    try {
      await api.delete(`/wishlist/product/${productId}`);
      await fetchWishlist();
    } catch (err) {
      console.error('Failed to remove from wishlist', err);
    }
  };

  const isInWishlist = (productId: number) => {
    return wishlistItems.some(item => item.product.productId === productId);
  };

  const clearWishlist = () => {
    setWishlistItems([]);
  };

  const totalItems = wishlistItems.length;

  return (
    <WishlistContext.Provider
      value={{
        wishlistItems,
        addToWishlist,
        removeFromWishlist,
        isInWishlist,
        clearWishlist,
        totalItems,
        loading
      }}
    >
      {children}
    </WishlistContext.Provider>
  );
}

export function useWishlist() {
  const context = useContext(WishlistContext);
  if (context === undefined) {
    throw new Error('useWishlist must be used within a WishlistProvider');
  }
  return context;
}