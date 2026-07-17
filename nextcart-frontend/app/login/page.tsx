'use client';

import { useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { useAuth } from '@/context/AuthContext';

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const { login, loading } = useAuth();
  const router = useRouter();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await login(email, password);
      router.push('/');
    } catch (error) {
      console.error('Login failed:', error);
    }
  };

  return (
    <div className="min-h-screen flex flex-col">
      {/* Top Navigation */}
      <header className="flex items-center justify-between border-b border-slate-200 dark:border-white/10 px-10 py-4 bg-white dark:bg-background-dark">
        <Link href="/" className="flex items-center gap-2 text-primary">
          <div className="size-6">
            <svg fill="none" viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg">
              <path d="M42.4379 44C42.4379 44 36.0744 33.9038 41.1692 24C46.8624 12.9336 42.2078 4 42.2078 4L7.01134 4C7.01134 4 11.6577 12.932 5.96912 23.9969C0.876273 33.9029 7.27094 44 7.27094 44L42.4379 44Z" fill="currentColor"></path>
            </svg>
          </div>
          <span className="text-slate-900 dark:text-white text-xl font-extrabold">NEXTCART</span>
        </Link>
        <div className="flex gap-4">
          <Link href="/" className="flex min-w-[100px] items-center justify-center rounded-xl h-10 px-4 bg-slate-100 dark:bg-white/5 text-slate-900 dark:text-white text-sm font-bold">
            Back to Home
          </Link>
        </div>
      </header>

      {/* Main Content */}
      <main className="flex-1 flex items-center justify-center p-6">
        <div className="w-full max-w-[480px] bg-white dark:bg-slate-900 rounded-2xl shadow-xl border border-slate-200 dark:border-slate-800 overflow-hidden">
          {/* Card Header */}
          <div className="pt-10 px-8">
            <div className="flex flex-col items-center text-center">
              <h4 className="text-primary text-xs font-extrabold uppercase tracking-widest mb-2">Customer Portal</h4>
              <h1 className="text-slate-900 dark:text-white text-3xl font-bold mb-2">Sign in to NEXTCART</h1>
              <p className="text-slate-500 dark:text-slate-400 text-base">Welcome back! Please enter your details.</p>
            </div>
          </div>

          {/* Login Form */}
          <form onSubmit={handleSubmit} className="p-8 space-y-6">
            {/* Email Field */}
            <div className="flex flex-col gap-2">
              <label className="text-slate-900 dark:text-white text-sm font-bold">Email Address</label>
              <div className="relative">
                <span className="material-icons-outlined absolute left-4 top-1/2 -translate-y-1/2 text-slate-400 text-xl">mail</span>
                <input
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="w-full pl-12 pr-4 py-4 rounded-xl border border-slate-200 dark:border-slate-700 bg-white dark:bg-slate-800 text-slate-900 dark:text-white placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-primary/20 transition-all"
                  placeholder="e.g. customer@example.com"
                  required
                />
              </div>
            </div>

            {/* Password Field */}
            <div className="flex flex-col gap-2">
              <div className="flex justify-between items-center">
                <label className="text-slate-900 dark:text-white text-sm font-bold">Password</label>
                <Link href="#" className="text-primary text-sm font-bold hover:underline">Forgot password?</Link>
              </div>
              <div className="relative">
                <span className="material-icons-outlined absolute left-4 top-1/2 -translate-y-1/2 text-slate-400 text-xl">lock</span>
                <input
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="w-full pl-12 pr-12 py-4 rounded-xl border border-slate-200 dark:border-slate-700 bg-white dark:bg-slate-800 text-slate-900 dark:text-white placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-primary/20 transition-all"
                  placeholder="Enter your password"
                  required
                />
                <button type="button" className="absolute right-4 top-1/2 -translate-y-1/2 text-slate-400 hover:text-primary transition-colors">
                  <span className="material-icons-outlined text-xl">visibility</span>
                </button>
              </div>
            </div>

            {/* Remember Me */}
            <div className="flex items-center gap-3">
              <input
                id="remember"
                type="checkbox"
                className="w-5 h-5 rounded border-slate-300 text-primary focus:ring-primary"
              />
              <label
                htmlFor="remember"
                className="text-sm font-medium text-slate-500 dark:text-slate-400 cursor-pointer"
              >
                Stay signed in for 30 days
              </label>
            </div>

            {/* Action Button */}
            <button
              type="submit"
              disabled={loading}
              className="w-full bg-primary hover:bg-primary/90 text-white font-bold py-4 rounded-xl shadow-lg shadow-primary/20 transition-all transform active:scale-[0.98] disabled:opacity-50"
            >
              {loading ? 'Signing in...' : 'Sign In'}
            </button>

            {/* Social Login Divider */}
            <div className="relative flex items-center py-2">
              <div className="flex-grow border-t border-slate-200 dark:border-slate-700"></div>
              <span className="flex-shrink mx-4 text-slate-400 text-xs font-bold uppercase tracking-wider">Or continue with</span>
              <div className="flex-grow border-t border-slate-200 dark:border-slate-700"></div>
            </div>

            {/* Social Buttons */}
            <div className="grid grid-cols-2 gap-4">
              <button type="button" className="flex items-center justify-center gap-2 py-3 px-4 rounded-xl border border-slate-200 dark:border-slate-700 hover:bg-slate-50 dark:hover:bg-slate-800 transition-colors">
                <span className="material-icons-outlined text-xl">account_circle</span>
                <span className="text-sm font-bold text-slate-900 dark:text-white">Google</span>
              </button>
              <button type="button" className="flex items-center justify-center gap-2 py-3 px-4 rounded-xl border border-slate-200 dark:border-slate-700 hover:bg-slate-50 dark:hover:bg-slate-800 transition-colors">
                <span className="material-icons-outlined text-xl">laptop_mac</span>
                <span className="text-sm font-bold text-slate-900 dark:text-white">Apple</span>
              </button>
            </div>
          </form>

          {/* Card Footer */}
          <div className="pb-10 px-8 text-center">
            <p className="text-sm font-medium text-slate-500 dark:text-slate-400">
              Don&apos;t have an account?
              <Link href="/register" className="text-primary font-bold hover:underline ml-1">Create an account</Link>
            </p>
          </div>
        </div>
      </main>

      {/* Page Footer */}
      <footer className="py-8 px-10 border-t border-slate-200 dark:border-slate-700">
        <div className="max-w-[1440px] mx-auto flex flex-col md:flex-row justify-between items-center gap-4">
          <p className="text-slate-500 dark:text-slate-400 text-xs font-medium">© 2024 NEXTCART Unified Commerce Platform. All rights reserved.</p>
          <div className="flex gap-6">
            <Link href="#" className="text-slate-500 dark:text-slate-400 text-xs font-bold hover:text-primary transition-colors">Privacy Policy</Link>
            <Link href="#" className="text-slate-500 dark:text-slate-400 text-xs font-bold hover:text-primary transition-colors">Terms of Service</Link>
            <Link href="#" className="text-slate-500 dark:text-slate-400 text-xs font-bold hover:text-primary transition-colors">Security</Link>
          </div>
        </div>
      </footer>
    </div>
  );
}
