import Link from 'next/link';

export default function Footer() {
  return (
    <footer className="bg-white dark:bg-background-dark border-t border-slate-200 dark:border-slate-800 mt-20 pt-16 pb-8">
      <div className="max-w-[1440px] mx-auto px-6 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-12 mb-16">
        <div className="space-y-6">
          <div className="flex items-center gap-2">
            <div className="w-8 h-8 bg-primary rounded-lg flex items-center justify-center text-white">
              <span className="material-icons-outlined text-sm">shopping_bag</span>
            </div>
            <span className="text-xl font-bold tracking-tight">
              NEX<span className="text-primary">CART</span>
            </span>
          </div>
          <p className="text-slate-500 dark:text-slate-400 leading-relaxed">
            Premium unified e-commerce platform delivering exceptional shopping experiences worldwide.
          </p>
          <div className="flex gap-4">
            <Link
              href="#"
              className="w-10 h-10 rounded-full bg-slate-100 dark:bg-slate-800 flex items-center justify-center text-slate-600 hover:text-primary transition-colors"
            >
              <span className="material-icons-outlined">facebook</span>
            </Link>
            <Link
              href="#"
              className="w-10 h-10 rounded-full bg-slate-100 dark:bg-slate-800 flex items-center justify-center text-slate-600 hover:text-primary transition-colors"
            >
              <span className="material-icons-outlined">camera_alt</span>
            </Link>
            <Link
              href="#"
              className="w-10 h-10 rounded-full bg-slate-100 dark:bg-slate-800 flex items-center justify-center text-slate-600 hover:text-primary transition-colors"
            >
              <span className="material-icons-outlined">alternate_email</span>
            </Link>
          </div>
        </div>

        <div className="space-y-6">
          <h4 className="font-bold uppercase tracking-widest text-sm">Quick Links</h4>
          <ul className="space-y-4 text-slate-500 dark:text-slate-400">
            <li>
              <Link href="#" className="hover:text-primary transition-colors">New Arrivals</Link>
            </li>
            <li>
              <Link href="#" className="hover:text-primary transition-colors">Best Sellers</Link>
            </li>
            <li>
              <Link href="#" className="hover:text-primary transition-colors">Gift Cards</Link>
            </li>
            <li>
              <Link href="#" className="hover:text-primary transition-colors">Sale Items</Link>
            </li>
          </ul>
        </div>

        <div className="space-y-6">
          <h4 className="font-bold uppercase tracking-widest text-sm">Support</h4>
          <ul className="space-y-4 text-slate-500 dark:text-slate-400">
            <li>
              <Link href="#" className="hover:text-primary transition-colors">Shipping Policy</Link>
            </li>
            <li>
              <Link href="#" className="hover:text-primary transition-colors">Returns & Exchanges</Link>
            </li>
            <li>
              <Link href="#" className="hover:text-primary transition-colors">FAQ</Link>
            </li>
            <li>
              <Link href="#" className="hover:text-primary transition-colors">Contact Us</Link>
            </li>
          </ul>
        </div>

        <div className="space-y-6">
          <h4 className="font-bold uppercase tracking-widest text-sm">Legal</h4>
          <ul className="space-y-4 text-slate-500 dark:text-slate-400">
            <li>
              <Link href="#" className="hover:text-primary transition-colors">Privacy Policy</Link>
            </li>
            <li>
              <Link href="#" className="hover:text-primary transition-colors">Terms of Service</Link>
            </li>
            <li>
              <Link href="#" className="hover:text-primary transition-colors">Cookie Policy</Link>
            </li>
            <li>
              <Link href="#" className="hover:text-primary transition-colors">Compliance</Link>
            </li>
          </ul>
        </div>
      </div>

      <div className="max-w-[1440px] mx-auto px-6 pt-8 border-t border-slate-200 dark:border-slate-800 flex flex-col md:flex-row justify-between items-center gap-4 text-slate-400 text-sm">
        <p>© 2024 NEXTCART Inc. All rights reserved.</p>
        <div className="flex gap-6">
          <span className="cursor-pointer hover:text-slate-600">English (US)</span>
          <span className="cursor-pointer hover:text-slate-600">USD</span>
        </div>
      </div>
    </footer>
  );
}
