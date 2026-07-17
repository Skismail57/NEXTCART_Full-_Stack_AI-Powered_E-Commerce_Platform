import Link from 'next/link';
import { useCart } from '@/context/CartContext';
import { useWishlist } from '@/context/WishlistContext';

export default function Header() {
  const { totalItems: cartItems } = useCart();
  const { totalItems: wishlistItems } = useWishlist();

  return (
    <header className="sticky top-0 z-50 w-full bg-white/80 dark:bg-background-dark/80 backdrop-blur-md border-b border-slate-200 dark:border-slate-800">
      <nav className="max-w-[1440px] mx-auto px-6 h-20 flex items-center justify-between gap-8">
        <Link href="/" className="flex items-center gap-2">
          <div className="w-10 h-10 bg-primary rounded-lg flex items-center justify-center text-white">
            <span className="material-icons-outlined">shopping_bag</span>
          </div>
          <span className="text-2xl font-bold tracking-tight">
            NEX<span className="text-primary">CART</span>
          </span>
        </Link>

        <div className="flex-1 max-w-2xl relative">
          <span className="material-icons-outlined absolute left-4 top-1/2 -translate-y-1/2 text-slate-400">
            search
          </span>
          <input
            className="w-full pl-12 pr-4 py-3 bg-slate-100 dark:bg-slate-800 border-none rounded-xl focus:ring-2 focus:ring-primary transition-all outline-none"
            placeholder="Search premium products..."
            type="text"
          />
        </div>

        <div className="flex items-center gap-6">
          <Link href="/products" className="flex items-center gap-2 font-medium hover:text-primary transition-colors">
            <span className="material-icons-outlined">explore</span>
            <span>Browse</span>
          </Link>
          <Link href="/wishlist" className="relative p-2 hover:bg-slate-100 dark:hover:bg-slate-800 rounded-full transition-all">
            <span className="material-icons-outlined">favorite_border</span>
            {wishlistItems > 0 && (
              <span className="absolute top-0 right-0 w-5 h-5 bg-red-500 text-white text-[10px] flex items-center justify-center rounded-full font-bold">
                {wishlistItems}
              </span>
            )}
          </Link>
          <Link href="/cart" className="relative p-2 hover:bg-slate-100 dark:hover:bg-slate-800 rounded-full transition-all">
            <span className="material-icons-outlined">shopping_cart</span>
            {cartItems > 0 && (
              <span className="absolute top-0 right-0 w-5 h-5 bg-primary text-white text-[10px] flex items-center justify-center rounded-full font-bold">
                {cartItems}
              </span>
            )}
          </Link>
          <Link href="/profile" className="w-10 h-10 rounded-full overflow-hidden border-2 border-slate-200 dark:border-slate-700 cursor-pointer">
            <img
              alt="Profile"
              className="w-full h-full object-cover"
              src="https://lh3.googleusercontent.com/aida-public/AB6AXuBgtNcaj_zp3HCPk7RPUrrEf0YTGOYKjRhoL1t3RAdIXHn9tOFHFs18txE_lUnSNy-XfDlbrUGr8Cd8HFiIT9n9727Rg153oCQ1It7UqRujIKvPpuHgAVhffR7DruIhIFi6zzHb9mkMuOGreF7Ls1fAXE9Waikmu_z7oDZZ4j-RGiw8JN1q5G8YURAD2kjnPA8ESbkfntatRwoxzyyylmbjC40q1T_QOEfh-gOt3G55GnEH7YSSofEhkvijz6BvHkNwYR1HGAdRKfz"
            />
          </Link>
        </div>
      </nav>
    </header>
  );
}
