import Link from 'next/link';
import { Product } from '@/types';
import { useCart } from '@/context/CartContext';
import { useWishlist } from '@/context/WishlistContext';

interface ProductCardProps {
  product: Product;
}

export default function ProductCard({ product }: ProductCardProps) {
  const { addToCart } = useCart();
  const { addToWishlist, removeFromWishlist, isInWishlist } = useWishlist();
  const primaryImage = product.images?.find((img) => img.isPrimary) || product.images?.[0];

  const handleAddToCart = (e: React.MouseEvent) => {
    e.stopPropagation();
    addToCart(product, 1);
  };

  const handleToggleWishlist = (e: React.MouseEvent) => {
    e.stopPropagation();
    if (isInWishlist(product.productId)) {
      removeFromWishlist(product.productId);
    } else {
      addToWishlist(product);
    }
  };

  return (
    <Link href={`/products/${product.productId}`} className="group">
      <div className="bg-white dark:bg-slate-900 rounded-xl overflow-hidden shadow-sm hover:shadow-xl transition-all border border-slate-100 dark:border-slate-800">
        <div className="relative aspect-[4/5] overflow-hidden">
          {primaryImage ? (
            <img
              alt={product.productName}
              className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
              src={primaryImage.imageUrl}
            />
          ) : (
            <div className="w-full h-full bg-slate-200 dark:bg-slate-700 flex items-center justify-center">
              <span className="material-icons-outlined text-4xl text-slate-400">image</span>
            </div>
          )}
          <button
            onClick={handleToggleWishlist}
            className="absolute top-4 right-4 p-2 bg-white/90 dark:bg-slate-800/90 backdrop-blur-sm rounded-full hover:scale-110 transition-all"
          >
            <span className={`material-icons-outlined ${isInWishlist(product.productId) ? 'text-red-500' : 'text-slate-400 hover:text-red-500'}`}>
              {isInWishlist(product.productId) ? 'favorite' : 'favorite_border'}
            </span>
          </button>
        </div>
        <div className="p-5 space-y-4">
          <div>
            <h3 className="font-bold text-lg group-hover:text-primary transition-colors">
              {product.productName}
            </h3>
            {product.shortDescription && (
              <p className="text-slate-500 text-sm dark:text-slate-400">
                {product.shortDescription}
              </p>
            )}
          </div>
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-2">
              {product.discountPrice ? (
                <>
                  <span className="text-2xl font-bold text-primary">
                    ${product.discountPrice.toFixed(2)}
                  </span>
                  <span className="text-sm text-slate-400 line-through">
                    ${product.price.toFixed(2)}
                  </span>
                </>
              ) : (
                <span className="text-2xl font-bold text-primary">
                  ${product.price.toFixed(2)}
                </span>
              )}
            </div>
            <button
              onClick={handleAddToCart}
              className="p-3 bg-primary/10 text-primary rounded-lg hover:bg-primary hover:text-white transition-all"
            >
              <span className="material-icons-outlined">add_shopping_cart</span>
            </button>
          </div>
        </div>
      </div>
    </Link>
  );
}
