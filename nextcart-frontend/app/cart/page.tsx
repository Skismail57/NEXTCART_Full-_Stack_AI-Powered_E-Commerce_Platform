'use client';

import Link from 'next/link';
import { useCart } from '@/context/CartContext';
import Header from '@/components/Header';
import Footer from '@/components/Footer';

export default function Cart() {
  const { cartItems, updateQuantity, removeFromCart, totalPrice, clearCart } = useCart();

  const taxRate = 0.08;
  const shippingRate = totalPrice > 100 ? 0 : 9.99;
  const tax = totalPrice * taxRate;
  const finalPrice = totalPrice + tax + shippingRate;

  if (cartItems.length === 0) {
    return (
      <div className="min-h-screen">
        <Header />
        <main className="max-w-[1440px] mx-auto px-6 py-16 text-center">
          <div className="max-w-md mx-auto">
            <div className="w-24 h-24 bg-slate-100 dark:bg-slate-800 rounded-full flex items-center justify-center mx-auto mb-6">
              <span className="material-icons-outlined text-5xl text-slate-400">shopping_cart</span>
            </div>
            <h2 className="text-3xl font-bold mb-4">Your cart is empty</h2>
            <p className="text-slate-500 dark:text-slate-400 mb-8">Looks like you haven&apos;t added anything to your cart yet.</p>
            <Link
              href="/"
              className="inline-flex items-center gap-2 px-8 py-4 bg-primary text-white rounded-lg font-bold hover:bg-blue-700 transition-all"
            >
              <span className="material-icons-outlined">arrow_back</span>
              Continue Shopping
            </Link>
          </div>
        </main>
        <Footer />
      </div>
    );
  }

  return (
    <div className="min-h-screen">
      <Header />
      <main className="max-w-[1440px] mx-auto px-6 py-12">
        <div className="mb-8">
          <Link href="/" className="flex items-center gap-2 text-primary hover:underline mb-4">
            <span className="material-icons-outlined text-sm">arrow_back</span>
            <span>Continue Shopping</span>
          </Link>
          <h1 className="text-3xl font-bold">Shopping Cart</h1>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Cart Items */}
          <div className="lg:col-span-2 space-y-4">
            {cartItems.map((item) => {
              const primaryImage = item.product.images?.find((img) => img.isPrimary) || item.product.images?.[0];
              return (
                <div key={item.id} className="bg-white dark:bg-slate-900 rounded-xl p-6 flex gap-6 border border-slate-200 dark:border-slate-800">
                  <div className="w-32 h-32 bg-slate-100 dark:bg-slate-800 rounded-lg overflow-hidden flex-shrink-0">
                    {primaryImage ? (
                      <img
                        src={primaryImage.imageUrl}
                        alt={primaryImage.altText || item.product.name}
                        className="w-full h-full object-cover"
                      />
                    ) : (
                      <div className="w-full h-full flex items-center justify-center">
                        <span className="material-icons-outlined text-4xl text-slate-400">image</span>
                      </div>
                    )}
                  </div>
                  <div className="flex-1 flex flex-col justify-between">
                    <div>
                      <div className="flex justify-between items-start">
                        <div>
                          <h3 className="font-bold text-lg">{item.product.name}</h3>
                          {item.product.shortDescription && (
                            <p className="text-slate-500 dark:text-slate-400 text-sm">{item.product.shortDescription}</p>
                          )}
                        </div>
                        <button
                          onClick={() => removeFromCart(item.id)}
                          className="text-slate-400 hover:text-red-500 transition-colors"
                        >
                          <span className="material-icons-outlined">delete</span>
                        </button>
                      </div>
                    </div>
                    <div className="flex justify-between items-center mt-4">
                      <div className="flex items-center gap-3">
                        <button
                          onClick={() => updateQuantity(item.id, item.quantity - 1)}
                          className="w-10 h-10 flex items-center justify-center border border-slate-200 dark:border-slate-700 rounded-lg hover:bg-slate-100 dark:hover:bg-slate-800 transition-colors"
                        >
                          <span className="material-icons-outlined">remove</span>
                        </button>
                        <span className="w-12 text-center font-bold">{item.quantity}</span>
                        <button
                          onClick={() => updateQuantity(item.id, item.quantity + 1)}
                          className="w-10 h-10 flex items-center justify-center border border-slate-200 dark:border-slate-700 rounded-lg hover:bg-slate-100 dark:hover:bg-slate-800 transition-colors"
                        >
                          <span className="material-icons-outlined">add</span>
                        </button>
                      </div>
                      <span className="text-xl font-bold text-primary">${(item.product.price * item.quantity).toFixed(2)}</span>
                    </div>
                  </div>
                </div>
              );
            })}

            <div className="flex justify-end">
              <button
                onClick={clearCart}
                className="flex items-center gap-2 text-slate-400 hover:text-red-500 transition-colors"
              >
                <span className="material-icons-outlined">delete_sweep</span>
                <span>Clear Cart</span>
              </button>
            </div>
          </div>

          {/* Order Summary */}
          <div className="lg:col-span-1">
            <div className="bg-white dark:bg-slate-900 rounded-xl p-6 border border-slate-200 dark:border-slate-800 sticky top-24">
              <h2 className="text-2xl font-bold mb-6">Order Summary</h2>

              <div className="space-y-4 mb-6">
                <div className="flex justify-between items-center">
                  <span className="text-slate-500 dark:text-slate-400">Subtotal</span>
                  <span className="font-bold">${totalPrice.toFixed(2)}</span>
                </div>
                <div className="flex justify-between items-center">
                  <span className="text-slate-500 dark:text-slate-400">Shipping</span>
                  <span className="font-bold">{shippingRate === 0 ? 'Free' : `$${shippingRate.toFixed(2)}`}</span>
                </div>
                <div className="flex justify-between items-center">
                  <span className="text-slate-500 dark:text-slate-400">Tax</span>
                  <span className="font-bold">${tax.toFixed(2)}</span>
                </div>
              </div>

              <div className="border-t border-slate-200 dark:border-slate-700 pt-4 mb-6">
                <div className="flex justify-between items-center">
                  <span className="font-bold text-lg">Total</span>
                  <span className="font-bold text-2xl text-primary">${finalPrice.toFixed(2)}</span>
                </div>
              </div>

              <button className="w-full py-4 bg-primary text-white rounded-lg font-bold hover:bg-blue-700 transition-all mb-4">
                Proceed to Checkout
              </button>

              <div className="flex items-center justify-center gap-2 text-slate-500 dark:text-slate-400 text-sm">
                <span className="material-icons-outlined text-base">lock</span>
                <span>Secure Checkout</span>
              </div>
            </div>
          </div>
        </div>
      </main>
      <Footer />
    </div>
  );
}
