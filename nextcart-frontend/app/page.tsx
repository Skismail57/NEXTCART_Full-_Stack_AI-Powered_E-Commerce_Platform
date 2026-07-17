'use client';

import { useEffect, useState } from 'react';
import Header from '@/components/Header';
import Footer from '@/components/Footer';
import ProductCard from '@/components/ProductCard';
import { Product, Category, ApiResponse } from '@/types';
import api from '@/lib/api';

export default function Home() {
  const [featuredProducts, setFeaturedProducts] = useState<Product[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    async function fetchData() {
      try {
        const [productsRes, categoriesRes] = await Promise.all([
          api.get<ApiResponse<Product[]>>('/products/featured?limit=8'),
          api.get<ApiResponse<Category[]>>('/categories'),
        ]);
        if (productsRes.data.success) {
          setFeaturedProducts(productsRes.data.data);
        }
        if (categoriesRes.data.success) {
          setCategories(categoriesRes.data.data);
        }
      } catch (error) {
        console.error('Error fetching data:', error);
        // Fallback to sample data if API is not available
        setFeaturedProducts(getSampleProducts());
        setCategories(getSampleCategories());
      }
    }
    fetchData();
  }, []);

  return (
    <div className="min-h-screen">
      {/* Promotional Ribbon */}
      <div className="w-full bg-primary py-2 text-center">
        <p className="text-white text-sm font-medium">
          Free shipping on all premium orders over $100.
          <span className="underline ml-2 cursor-pointer">Details</span>
        </p>
      </div>

      <Header />

      <main className="max-w-[1440px] mx-auto px-6 py-8 space-y-16">
        {/* Hero Section */}
        <section className="relative w-full h-[500px] rounded-xl overflow-hidden group">
          <img
            alt="Hero Lifestyle"
            className="absolute inset-0 w-full h-full object-cover"
            src="https://images.unsplash.com/photo-1441986300917-64674bd600d8?w=1920&h=800&fit=crop"
          />
          <div className="absolute inset-0 bg-gradient-to-r from-black/60 to-transparent flex flex-col justify-center px-16">
            <span className="text-primary font-bold tracking-widest uppercase mb-4">New Collection 2024</span>
            <h1 className="text-5xl md:text-6xl font-bold text-white mb-6 leading-tight max-w-2xl">
              Elevate Your Lifestyle Selection
            </h1>
            <p className="text-slate-200 text-xl mb-8 max-w-lg leading-relaxed">
              Experience the intersection of luxury and utility with our curated premium essentials.
            </p>
            <div className="flex gap-4">
              <button className="px-8 py-4 bg-primary text-white rounded-lg font-bold hover:bg-blue-700 transition-all shadow-lg shadow-primary/20">
                Shop Collection
              </button>
              <button className="px-8 py-4 bg-white/10 backdrop-blur-md text-white border border-white/20 rounded-lg font-bold hover:bg-white/20 transition-all">
                View Lookbook
              </button>
            </div>
          </div>
        </section>

        {/* Categories Section */}
        {categories.length > 0 && (
          <section className="space-y-8">
            <div className="flex justify-between items-end">
              <div>
                <h2 className="text-3xl font-bold">Shop by Category</h2>
                <p className="text-slate-500 dark:text-slate-400 mt-2">
                  Explore our wide range of premium products
                </p>
              </div>
              <button className="text-primary font-bold flex items-center gap-1 hover:underline">
                View All Categories
                <span className="material-icons-outlined text-sm">arrow_forward</span>
              </button>
            </div>
            <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
              {categories.map((category) => (
                <div key={category.categoryId} className="group cursor-pointer text-center space-y-4">
                  <div className="aspect-square bg-white dark:bg-slate-800 rounded-2xl flex items-center justify-center shadow-sm border border-slate-100 dark:border-slate-800 group-hover:border-primary group-hover:shadow-xl group-hover:shadow-primary/5 transition-all">
                    <span className="material-icons-outlined text-4xl text-slate-700 dark:text-slate-300 group-hover:text-primary transition-colors">
                      {getCategoryIcon(category.name)}
                    </span>
                  </div>
                  <p className="font-bold">{category.name}</p>
                </div>
              ))}
            </div>
          </section>
        )}

        {/* Featured Products Section */}
        {featuredProducts.length > 0 && (
          <section className="space-y-8">
            <div className="flex justify-between items-end">
              <div>
                <h2 className="text-3xl font-bold">Featured Arrivals</h2>
                <p className="text-slate-500 dark:text-slate-400 mt-2">
                  The latest and greatest from NEXTCART
                </p>
              </div>
              <div className="flex gap-2">
                <button className="p-2 border border-slate-200 dark:border-slate-800 rounded-lg hover:bg-slate-50 dark:hover:bg-slate-800 transition-colors">
                  <span className="material-icons-outlined">chevron_left</span>
                </button>
                <button className="p-2 border border-slate-200 dark:border-slate-800 rounded-lg hover:bg-slate-50 dark:hover:bg-slate-800 transition-colors">
                  <span className="material-icons-outlined">chevron_right</span>
                </button>
              </div>
            </div>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">
              {featuredProducts.map((product) => (
                <ProductCard key={product.productId} product={product} />
              ))}
            </div>
          </section>
        )}

        {/* Newsletter Section */}
        <section className="bg-slate-900 rounded-2xl p-12 md:p-16 text-center text-white relative overflow-hidden">
          <div className="absolute top-0 right-0 w-64 h-64 bg-primary/20 rounded-full blur-[100px] -translate-y-1/2 translate-x-1/2"></div>
          <div className="absolute bottom-0 left-0 w-64 h-64 bg-primary/10 rounded-full blur-[100px] translate-y-1/2 -translate-x-1/2"></div>
          <div className="relative z-10 max-w-2xl mx-auto space-y-6">
            <h2 className="text-3xl md:text-4xl font-bold tracking-tight">Stay in the Loop</h2>
            <p className="text-slate-400 text-lg">
              Join our newsletter to receive updates on new collections, exclusive offers, and premium insights.
            </p>
            <div className="flex flex-col sm:flex-row gap-4 max-w-md mx-auto">
              <input
                className="flex-1 bg-white/10 border-white/20 rounded-lg px-4 py-3 focus:ring-2 focus:ring-primary outline-none text-white"
                placeholder="Your email address"
                type="email"
              />
              <button className="bg-primary hover:bg-blue-600 px-6 py-3 rounded-lg font-bold transition-all">
                Subscribe
              </button>
            </div>
            <p className="text-slate-500 text-xs">
              By subscribing, you agree to our Terms of Service and Privacy Policy.
            </p>
          </div>
          </section>
      </main>

      <Footer />
    </div>
  );
}

function getCategoryIcon(categoryName: string): string {
  const lowerName = categoryName.toLowerCase();
  if (lowerName.includes('electronics')) return 'devices';
  if (lowerName.includes('fashion')) return 'checkroom';
  if (lowerName.includes('home') || lowerName.includes('kitchen')) return 'laptop_mac';
  if (lowerName.includes('beauty')) return 'face';
  if (lowerName.includes('sports')) return 'sports_score';
  return 'shopping_bag';
}

function getSampleProducts(): Product[] {
  return [
    {
      productId: 1,
      categoryId: 1,
      brandId: 4,
      productName: 'Acoustic Pro Headphones',
      slug: 'acoustic-pro-headphones',
      description: 'Premium wireless headphones with active noise cancellation.',
      shortDescription: 'Noise Cancelling Wireless',
      price: 299.99,
      featured: true,
      stock: 50,
      images: [
        {
          imageId: 1,
          productId: 1,
          imageUrl: 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=800&h=800&fit=crop',
          isPrimary: true,
        },
      ],
      variants: [],
    },
    {
      productId: 2,
      categoryId: 1,
      brandId: 1,
      productName: 'Chronos Gold Series',
      slug: 'chronos-gold-series',
      description: 'Luxury minimalist timepiece with premium materials.',
      shortDescription: 'Limited Edition Timepiece',
      price: 449.99,
      featured: true,
      stock: 30,
      images: [
        {
          imageId: 2,
          productId: 2,
          imageUrl: 'https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=800&h=800&fit=crop',
          isPrimary: true,
        },
      ],
      variants: [],
    },
  ];
}

function getSampleCategories(): Category[] {
  return [
    {
      categoryId: 1,
      name: 'Electronics',
      slug: 'electronics',
      description: 'All electronic gadgets and devices',
    },
    {
      categoryId: 2,
      name: 'Fashion',
      slug: 'fashion',
      description: 'Trendy clothing and accessories',
    },
    {
      categoryId: 3,
      name: 'Home & Kitchen',
      slug: 'home-kitchen',
      description: 'Home appliances and kitchen essentials',
    },
    {
      categoryId: 4,
      name: 'Beauty',
      slug: 'beauty',
      description: 'Beauty and personal care products',
    },
    {
      categoryId: 5,
      name: 'Sports',
      slug: 'sports',
      description: 'Sports equipment and accessories',
    },
  ];
}
