'use client'

import Link from 'next/link'
import { useWishlist } from '@/context/WishlistContext'
import { useCart } from '@/context/CartContext'
import Header from '@/components/Header'
import Footer from '@/components/Footer'
import ProductCard from '@/components/ProductCard'

export default function WishlistPage() {
  const { wishlistItems, removeFromWishlist, clearWishlist, totalItems } = useWishlist()
  const { addToCart } = useCart()

  const handleAddAllToCart = () => {
    wishlistItems.forEach(item => {
      addToCart(item.product, 1)
    })
  }

  if (wishlistItems.length === 0) {
    return (
      <div className="min-h-screen">
        <Header />
        <main className="max-w-[1440px] mx-auto px-6 py-16 text-center">
          <div className="max-w-md mx-auto">
            <div className="w-24 h-24 bg-slate-100 dark:bg-slate-800 rounded-full flex items-center justify-center mx-auto mb-6">
              <span className="material-icons-outlined text-5xl text-slate-400">favorite_border</span>
            </div>
            <h2 className="text-3xl font-bold mb-4">Your wishlist is empty</h2>
            <p className="text-slate-500 dark:text-slate-400 mb-8">Save items you love for later!</p>
            <Link
              href="/products"
              className="inline-flex items-center gap-2 px-8 py-4 bg-primary text-white rounded-lg font-bold hover:bg-blue-700 transition-all"
            >
              <span className="material-icons-outlined">explore</span>
              Browse Products
            </Link>
          </div>
        </main>
        <Footer />
      </div>
    )
  }

  return (
    <div className="min-h-screen">
      <Header />
      <main className="max-w-[1440px] mx-auto px-6 py-12">
        <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-8">
          <div>
            <h1 className="text-3xl font-bold">Your Wishlist</h1>
            <p className="text-slate-500 dark:text-slate-400 mt-1">
              {totalItems} {totalItems === 1 ? 'item' : 'items'} saved
            </p>
          </div>
          <div className="flex gap-3">
            <button
              onClick={clearWishlist}
              className="px-4 py-2 border border-slate-200 dark:border-slate-700 rounded-lg text-slate-600 dark:text-slate-300 hover:bg-slate-100 dark:hover:bg-slate-800 transition-all"
            >
              Clear All
            </button>
            <button
              onClick={handleAddAllToCart}
              className="px-4 py-2 bg-primary text-white rounded-lg font-medium hover:bg-blue-700 transition-all"
            >
              Add All to Cart
            </button>
          </div>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
          {wishlistItems.map((item) => (
            <ProductCard key={item.id} product={item.product} />
          ))}
        </div>
      </main>
      <Footer />
    </div>
  )
}
