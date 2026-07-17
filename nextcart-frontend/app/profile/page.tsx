'use client'

import { useAuth } from '@/context/AuthContext'
import Header from '@/components/Header'
import Footer from '@/components/Footer'

export default function ProfilePage() {
  const { user, logout } = useAuth()

  if (!user) {
    return (
      <div className="min-h-screen">
        <Header />
        <main className="max-w-[1440px] mx-auto px-6 py-16 text-center">
          <h2 className="text-3xl font-bold mb-4">Please login to view your profile</h2>
          <a href="/login" className="text-primary hover:underline font-medium">Login here</a>
        </main>
        <Footer />
      </div>
    )
  }

  return (
    <div className="min-h-screen">
      <Header />
      <main className="max-w-[1440px] mx-auto px-6 py-16">
        <h1 className="text-4xl font-bold mb-8">Your Profile</h1>
        <div className="bg-white dark:bg-slate-900 p-8 rounded-xl border border-slate-200 dark:border-slate-800 shadow-sm">
          <div className="flex items-center gap-6 mb-6">
            <div className="w-24 h-24 rounded-full bg-primary flex items-center justify-center text-white text-3xl font-bold">
              {user.firstName?.charAt(0)}{user.lastName?.charAt(0)}
            </div>
            <div>
              <h2 className="text-2xl font-bold">{user.firstName} {user.lastName}</h2>
              <p className="text-slate-500 dark:text-slate-400">{user.email}</p>
              {user.phone && <p className="text-slate-500 dark:text-slate-400">{user.phone}</p>}
            </div>
          </div>
          <button
            onClick={logout}
            className="px-6 py-3 bg-red-500 hover:bg-red-600 text-white font-medium rounded-lg transition-colors"
          >
            Logout
          </button>
        </div>
      </main>
      <Footer />
    </div>
  )
}
