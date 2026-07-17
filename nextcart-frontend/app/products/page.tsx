'use client'

import { useState, useEffect } from 'react'
import Header from '@/components/Header'
import Footer from '@/components/Footer'
import ProductCard from '@/components/ProductCard'
import { Product, Category, ApiResponse } from '@/types'
import api from '@/lib/api'

const sortOptions = [
  { value: 'featured', label: 'Featured' },
  { value: 'price-low', label: 'Price: Low to High' },
  { value: 'price-high', label: 'Price: High to Low' },
  { value: 'rating', label: 'Highest Rated' },
  { value: 'newest', label: 'Newest' }
]

export default function ProductsPage() {
  const [products, setProducts] = useState<Product[]>([])
  const [categories, setCategories] = useState<Category[]>([])
  const [selectedCategory, setSelectedCategory] = useState<number | null>(null)
  const [sortBy, setSortBy] = useState('featured')
  const [priceRange, setPriceRange] = useState([0, 2000])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    async function fetchData() {
      setLoading(true)
      try {
        const [productsRes, categoriesRes] = await Promise.all([
          api.get<ApiResponse<Product[]>>('/products'),
          api.get<ApiResponse<Category[]>>('/categories'),
        ])
        if (productsRes.data.success) {
          setProducts(productsRes.data.data)
        }
        if (categoriesRes.data.success) {
          setCategories(categoriesRes.data.data)
        }
      } catch (error) {
        console.error('Error fetching data:', error)
        // Fallback to sample data if API is not available
        setProducts(getSampleProducts())
        setCategories(getSampleCategories())
      } finally {
        setLoading(false)
      }
    }
    fetchData()
  }, [])

  return (
    <div className="min-h-screen">
      <Header />
      <main className="max-w-[1440px] mx-auto px-6 py-12">
        <h1 className="text-3xl font-bold mb-8">All Products</h1>
        
        <div className="flex flex-col lg:flex-row gap-8">
          {/* Filters Sidebar */}
          <aside className="lg:w-64 flex-shrink-0">
            <div className="bg-white dark:bg-slate-900 rounded-xl p-6 border border-slate-100 dark:border-slate-800">
              <h3 className="font-bold text-lg mb-4">Categories</h3>
              <div className="space-y-2">
                <button
                  onClick={() => setSelectedCategory(null)}
                  className={`w-full text-left px-3 py-2 rounded-lg transition-all ${
                    selectedCategory === null
                      ? 'bg-primary/10 text-primary font-medium'
                      : 'text-slate-600 dark:text-slate-300 hover:bg-slate-100 dark:hover:bg-slate-800'
                  }`}
                >
                  All Products
                </button>
                {categories.map((cat) => (
                  <button
                    key={cat.categoryId}
                    onClick={() => setSelectedCategory(cat.categoryId)}
                    className={`w-full text-left px-3 py-2 rounded-lg transition-all ${
                      selectedCategory === cat.categoryId
                        ? 'bg-primary/10 text-primary font-medium'
                        : 'text-slate-600 dark:text-slate-300 hover:bg-slate-100 dark:hover:bg-slate-800'
                    }`}
                  >
                    {cat.name}
                  </button>
                ))}
              </div>

              <div className="h-px bg-slate-200 dark:bg-slate-700 my-6"></div>

              <h3 className="font-bold text-lg mb-4">Price Range</h3>
              <div className="space-y-4">
                <div className="flex items-center gap-4">
                  <span className="text-slate-500 dark:text-slate-400">${priceRange[0]}</span>
                  <input
                    type="range"
                    min="0"
                    max="2000"
                    value={priceRange[1]}
                    onChange={(e) => setPriceRange([0, Number(e.target.value)])}
                    className="flex-1"
                  />
                  <span className="text-slate-500 dark:text-slate-400">${priceRange[1]}</span>
                </div>
              </div>

              <div className="h-px bg-slate-200 dark:bg-slate-700 my-6"></div>

              <h3 className="font-bold text-lg mb-4">Filters</h3>
              <div className="space-y-4">
                <div>
                  <label className="flex items-center gap-2 cursor-pointer">
                    <input type="checkbox" className="rounded border-slate-300 text-primary focus:ring-primary" />
                    <span className="text-slate-600 dark:text-slate-300">On Sale</span>
                  </label>
                </div>
                <div>
                  <label className="flex items-center gap-2 cursor-pointer">
                    <input type="checkbox" className="rounded border-slate-300 text-primary focus:ring-primary" />
                    <span className="text-slate-600 dark:text-slate-300">New Arrivals</span>
                  </label>
                </div>
                <div>
                  <label className="flex items-center gap-2 cursor-pointer">
                    <input type="checkbox" className="rounded border-slate-300 text-primary focus:ring-primary" />
                    <span className="text-slate-600 dark:text-slate-300">In Stock</span>
                  </label>
                </div>
              </div>
            </div>
          </aside>

          {/* Products Grid */}
          <div className="flex-1">
            <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-6">
              <p className="text-slate-500 dark:text-slate-400">
                Showing {products.length} products
              </p>
              <div className="flex items-center gap-2">
                <span className="text-slate-500 dark:text-slate-400">Sort by:</span>
                <select
                  value={sortBy}
                  onChange={(e) => setSortBy(e.target.value)}
                  className="px-3 py-2 border border-slate-200 dark:border-slate-700 rounded-lg bg-white dark:bg-slate-900 text-slate-900 dark:text-white"
                >
                  {sortOptions.map((opt) => (
                    <option key={opt.value} value={opt.value}>
                      {opt.label}
                    </option>
                  ))}
                </select>
              </div>
            </div>

            {loading ? (
              <div className="text-center py-12">Loading...</div>
            ) : (
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
                {products.map((product) => (
                  <ProductCard key={product.productId} product={product} />
                ))}
              </div>
            )}
          </div>
        </div>
      </main>
      <Footer />
    </div>
  )
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
  ]
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
  ]
}
