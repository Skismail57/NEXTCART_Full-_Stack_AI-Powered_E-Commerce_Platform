export interface Product {
  productId: number;
  categoryId?: number;
  subCategoryId?: number;
  brandId?: number;
  productName: string;
  slug: string;
  sku?: string;
  shortDescription?: string;
  description?: string;
  price: number;
  discountPrice?: number;
  tax?: number;
  stock?: number;
  weight?: number;
  warranty?: string;
  status?: 'ACTIVE' | 'INACTIVE' | 'OUT_OF_STOCK';
  featured?: boolean;
  createdAt?: string;
  updatedAt?: string;
  categoryName?: string;
  brandName?: string;
  images: ProductImage[];
  variants: ProductVariant[];
}

export interface ProductImage {
  imageId: number;
  productId: number;
  imageUrl: string;
  displayOrder?: number;
  isPrimary: boolean;
  createdAt?: string;
}

export interface ProductVariant {
  variantId: number;
  productId: number;
  color?: string;
  size?: string;
  ram?: string;
  storage?: string;
  price?: number;
  stock?: number;
  sku?: string;
  status?: 'ACTIVE' | 'INACTIVE';
}

export interface Category {
  categoryId: number;
  name: string;
  slug: string;
  image?: string;
  description?: string;
  status?: 'ACTIVE' | 'INACTIVE';
  createdAt?: string;
  updatedAt?: string;
}

export interface SubCategory {
  subCategoryId: number;
  categoryId: number;
  name: string;
  slug: string;
  image?: string;
  description?: string;
  status?: 'ACTIVE' | 'INACTIVE';
  createdAt?: string;
}

export interface Brand {
  brandId: number;
  brandName: string;
  brandLogo?: string;
  description?: string;
  status?: 'ACTIVE' | 'INACTIVE';
  createdAt?: string;
}

export interface User {
  userId: number;
  roleId?: number;
  roleName?: string;
  firstName: string;
  lastName: string;
  email: string;
  mobile?: string;
  password: string;
  profileImage?: string;
  gender?: string;
  dateOfBirth?: string;
  status?: string;
  emailVerified?: boolean;
  mobileVerified?: boolean;
  createdAt?: string;
  updatedAt?: string;
}

export interface CartItem {
  cartItemId: number;
  cartId: number;
  productId: number;
  variantId?: number;
  quantity: number;
  price: number;
  total: number;
}

export interface WishlistItem {
  wishlistId: number;
  userId: number;
  productId: number;
  createdAt?: string;
}

export interface Coupon {
  couponId: number;
  couponCode: string;
  discountType: 'PERCENTAGE' | 'FIXED';
  discountValue: number;
  minimumAmount?: number;
  maximumDiscount?: number;
  expiryDate: string;
  usageLimit?: number;
  status?: 'ACTIVE' | 'INACTIVE';
  createdAt?: string;
}

export interface Banner {
  bannerId: number;
  title: string;
  imageUrl: string;
  redirectUrl?: string;
  displayOrder?: number;
  status?: 'ACTIVE' | 'INACTIVE';
  createdAt?: string;
}

export interface HeroBanner {
  heroId: number;
  title: string;
  subtitle?: string;
  imageUrl: string;
  buttonText?: string;
  buttonLink?: string;
  displayOrder?: number;
  status?: 'ACTIVE' | 'INACTIVE';
}

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
  errors?: any[];
  timestamp?: string;
}
