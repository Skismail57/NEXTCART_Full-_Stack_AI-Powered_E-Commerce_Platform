'use client'

import { useState, useEffect } from 'react'
import Link from 'next/link'
import { useParams, useRouter } from 'next/navigation'
import { Product } from '@/types'
import Header from '@/components/Header'
import Footer from '@/components/Footer'
import ProductCard from '@/components/ProductCard'
import { useCart } from '@/context/CartContext'
import { useWishlist } from '@/context/WishlistContext'
import api from '@/lib/api'
import { ApiResponse } from '@/types'

export default function ProductDetail() {
  const params = useParams()
  const router = useRouter()
  const { addToCart } = useCart()
  const { addToWishlist, removeFromWishlist, isInWishlist } = useWishlist()
  const [product, setProduct] = useState<Product | null>(null)
  const [relatedProducts, setRelatedProducts] = useState<Product[]>([])
  const [selectedImage, setSelectedImage] = useState(0)
  const [quantity, setQuantity] = useState(1)
  const [activeTab, setActiveTab] = useState('description')
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    async function fetchData() {
      setLoading(true)
      try {
        const id = Number(params.id)
        const productRes = await api.get<ApiResponse<Product>>(`/products/${id}`)
        if (productRes.data.success) {
          setProduct(productRes.data.data)
        }
        
        // Fetch related products
        const productsRes = await api.get<ApiResponse<Product[]>>('/products')
        if (productsRes.data.success) {
          setRelatedProducts(productsRes.data.data.filter(p => p.productId !== id).slice(0, 4))
        }
      } catch (error) {
        console.error('Error fetching data:', error)
        // Fallback to sample data
        const id = Number(params.id)
        setProduct(getSampleProduct(id))
        setRelatedProducts(getRelatedProducts())
      } finally {
        setLoading(false)
      }
    }
    if (params.id) {
      fetchData()
    }
  }, [params.id])

  if (loading) {
    return (
      <div className="min-h-screen">
        <Header />
        <main className="max-w-[1440px] mx-auto px-6 py-16 text-center">
          <h2 className="text-2xl font-bold">Loading...</h2>
        </main>
        <Footer />
      </div>
    )
  }

  if (!product) {
    return (
      <div className="min-h-screen">
        <Header />
        <main className="max-w-[1440px] mx-auto px-6 py-16 text-center">
          <h2 className="text-2xl font-bold">Product not found</h2>
        </main>
        <Footer />
      </div>
    )
  }

  const handleAddToCart = () => {
    addToCart(product, quantity)
    router.push('/cart')
  }

  const handleToggleWishlist = () => {
    if (isInWishlist(product.productId)) {
      removeFromWishlist(product.productId)
    } else {
      addToWishlist(product)
    }
  }

  return (
    <div className="min-h-screen">
      <Header />
      <main className="max-w-[1440px] mx-auto px-6 py-6">
        {/* Breadcrumbs */}
        <div className="flex flex-wrap gap-2 py-4">
          <Link href="/" className="text-slate-500 dark:text-slate-400 text-sm font-medium flex items-center gap-1 hover:text-primary transition-colors">
            Home <span className="material-icons-outlined text-xs">chevron_right</span>
          </Link>
          <Link href="/products" className="text-slate-500 dark:text-slate-400 text-sm font-medium flex items-center gap-1 hover:text-primary transition-colors">
            Products <span className="material-icons-outlined text-xs">chevron_right</span>
          </Link>
          <span className="text-slate-900 dark:text-white text-sm font-medium">{product.productName}</span>
        </div>

        {/* Product Section */}
        <div className="grid grid-cols-1 lg:grid-cols-12 gap-12 mt-4">
          {/* Left Column: Gallery */}
          <div className="lg:col-span-7 flex flex-col gap-4">
            <div className="w-full bg-white dark:bg-slate-900 rounded-xl overflow-hidden aspect-square flex items-center justify-center p-8 border border-slate-100 dark:border-slate-800">
              {product.images.length > 0 && (
                <img
                  src={product.images[selectedImage].imageUrl}
                  alt={product.images[selectedImage].altText || product.productName}
                  className="w-full h-full object-contain"
                />
              )}
            </div>
            {/* Thumbnails */}
            <div className="flex overflow-x-auto gap-4 pb-2">
              {product.images.map((img, index) => (
                <button
                  key={img.imageId}
                  onClick={() => setSelectedImage(index)}
                  className={`flex-shrink-0 w-24 h-24 rounded-lg overflow-hidden cursor-pointer bg-white dark:bg-slate-900 border-2 transition-all ${
                    selectedImage === index ? 'border-primary' : 'border-transparent hover:border-primary/50'
                  }`}
                >
                  <img
                    src={img.imageUrl}
                    alt={img.altText || product.productName}
                    className="w-full h-full object-cover"
                  />
                </button>
              ))}
            </div>
          </div>

          {/* Right Column: Info & Purchase */}
          <div className="lg:col-span-5 flex flex-col gap-6">
            <div className="flex flex-col gap-2">
              <div className="flex items-center gap-2">
                {product.featured && (
                  <span className="px-2 py-0.5 rounded text-[10px] font-bold uppercase tracking-wider bg-primary/10 text-primary">
                    Featured
                  </span>
                )}
                {product.discountPrice && (
                  <span className="px-2 py-0.5 rounded text-[10px] font-bold uppercase tracking-wider bg-red-100 text-red-600">
                    Sale
                  </span>
                )}
              </div>
              <h1 className="text-slate-900 dark:text-white text-4xl font-black leading-tight">
                {product.productName}
              </h1>
            </div>

            <div className="flex flex-col gap-1">
              <div className="flex items-baseline gap-3">
                <span className="text-4xl font-bold text-primary">
                  ${(product.discountPrice || product.price).toFixed(2)}
                </span>
                {product.discountPrice && (
                  <span className="text-slate-500 text-lg line-through">
                    ${product.price.toFixed(2)}
                  </span>
                )}
              </div>
              <p className="text-slate-500 text-sm">
                Or 4 interest-free payments of ${((product.discountPrice || product.price) / 4).toFixed(2)}
              </p>
            </div>

            <div className="h-px bg-slate-200 dark:bg-slate-700 w-full"></div>

            {/* Quantity */}
            <div className="flex flex-col gap-4">
              <p className="text-slate-900 dark:text-white font-bold">Quantity</p>
              <div className="flex items-center gap-4">
                <div className="flex border border-slate-200 dark:border-slate-700 rounded-lg overflow-hidden h-12">
                  <button
                    onClick={() => setQuantity(Math.max(1, quantity - 1))}
                    className="px-4 hover:bg-slate-100 dark:hover:bg-slate-800 transition-colors"
                  >
                    <span className="material-icons-outlined">remove</span>
                  </button>
                  <input
                    type="number"
                    value={quantity}
                    onChange={(e) => setQuantity(Math.max(1, Number(e.target.value)))}
                    className="w-14 border-none text-center bg-transparent focus:ring-0"
                  />
                  <button
                    onClick={() => setQuantity(quantity + 1)}
                    className="px-4 hover:bg-slate-100 dark:hover:bg-slate-800 transition-colors"
                  >
                    <span className="material-icons-outlined">add</span>
                  </button>
                </div>
                <span className="text-green-600 text-sm font-medium flex items-center gap-1">
                  <span className="material-icons-outlined text-sm">check_circle</span>
                  {product.stock && product.stock > 0 ? 'In Stock' : 'Out of Stock'}
                </span>
              </div>
            </div>

            {/* Action Buttons */}
            <div className="flex flex-col gap-3 mt-4">
              <button
                onClick={handleAddToCart}
                disabled={!product.stock || product.stock <= 0}
                className="w-full py-4 rounded-xl bg-primary text-white font-bold text-lg hover:bg-primary/90 transition-all shadow-lg shadow-primary/20 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                Buy It Now
              </button>
              <button
                onClick={handleAddToCart}
                disabled={!product.stock || product.stock <= 0}
                className="w-full py-4 rounded-xl bg-white dark:bg-slate-900 border-2 border-primary text-primary font-bold text-lg hover:bg-primary hover:text-white transition-all disabled:opacity-50 disabled:cursor-not-allowed"
              >
                Add to Cart
              </button>
              <button
                onClick={handleToggleWishlist}
                className="w-full py-4 rounded-xl border border-slate-200 dark:border-slate-700 text-slate-900 dark:text-white font-bold text-lg hover:bg-slate-100 dark:hover:bg-slate-800 transition-all flex items-center justify-center gap-2"
              >
                <span className={`material-icons-outlined ${isInWishlist(product.productId) ? 'text-red-500' : ''}`}>
                  {isInWishlist(product.productId) ? 'favorite' : 'favorite_border'}
                </span>
                {isInWishlist(product.productId) ? 'Remove from Wishlist' : 'Add to Wishlist'}
              </button>
            </div>

            {/* Description */}
            {product.description && (
              <div className="mt-4">
                <p className="text-slate-600 dark:text-slate-300 leading-relaxed">
                  {product.description}
                </p>
              </div>
            )}
          </div>
        </div>

        {/* Related Products */}
        {relatedProducts.length > 0 && (
          <div className="mt-20 mb-20">
            <h3 className="text-2xl font-bold mb-8">You May Also Like</h3>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
              {relatedProducts.map((p) => (
                <ProductCard key={p.productId} product={p} />
              ))}
            </div>
          </div>
        )}
      </main>
      <Footer />
    </div>
  )
}

function getSampleProduct(id: number): Product {
  return {
    productId: id,
    categoryId: 1,
    brandId: 1,
    productName: 'SonicPro Ultra Wireless Headphones',
    slug: 'sonicpro-ultra',
    description: 'The SonicPro Ultra Wireless Headphones redefine premium audio.',
    shortDescription: 'Premium Wireless Noise Cancelling Headphones',
    price: 349.99,
    discountPrice: 429.99,
    featured: true,
    stock: 50,
    images: [
      {
        imageId: 1,
        productId: id,
        imageUrl: 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=800&h=800&fit=crop',
        altText: 'SonicPro Ultra Headphones',
        isPrimary: true,
      },
    ],
    variants: [],
  }
}

function getRelatedProducts(): Product[] {
  return [
    {
      productId: 2,
      categoryId: 1,
      brandId: 1,
      productName: 'Sonic Buds Air',
      slug: 'sonic-buds-air',
      description: 'Compact wireless earbuds with premium sound',
      shortDescription: 'True Wireless Earbuds',
      price: 129.99,
      featured: true,
      stock: 100,
      images: [
        {
          imageId: 3,
          productId: 2,
          imageUrl: 'https://images.unsplash.com/photo-1600267214595-567c6e790029?w=800&h=800&fit=crop',
          altText: 'Sonic Buds Air',
          isPrimary: true,
        },
      ],
      variants: [],
    },
  ]
}
