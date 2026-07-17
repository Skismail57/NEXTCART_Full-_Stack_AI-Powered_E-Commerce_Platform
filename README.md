# NEXTCART - Full Stack E-Commerce Platform

A complete full-stack e-commerce application built with Next.js (frontend) and Core Java (backend).

## Project Structure

```
NEXTCART E-COMMERCE/
├── nextcart-frontend/       # Next.js + React + TypeScript Frontend
├── nextcart-backend/        # Core Java + Jakarta EE Backend
├── database/                # MySQL Database Schema
├── NEXTCART FRONTEND/       # Original HTML templates
├── docs/                    # Documentation
├── api-docs/                # API Documentation
└── README.md                # This file
```

## Technology Stack

### Frontend
- **Next.js 14** - React framework
- **React 18** - UI library
- **TypeScript** - Type safety
- **Tailwind CSS** - Utility-first CSS
- **Axios** - HTTP client
- **Material Icons** - Icon library

### Backend
- **Core Java 17** - Programming language
- **Jakarta EE 10** - Enterprise platform
- **JAX-RS (Jersey)** - RESTful web services
- **JDBC** - Database connectivity
- **HikariCP** - Connection pooling
- **JWT** - Authentication
- **BCrypt** - Password hashing
- **Maven** - Build tool

### Database
- **MySQL 8** - Relational database

## Getting Started

### Prerequisites
- Node.js 18+
- Java 17+
- Maven 3.8+
- MySQL 8+

### Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE nextcart CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Import the schema:
```bash
mysql -u root -p nextcart < database/schema.sql
```

3. Update database credentials in:
   - `nextcart-backend/src/main/resources/config.properties`

### Backend Setup

1. Navigate to the backend directory:
```bash
cd nextcart-backend
```

2. Build the project:
```bash
mvn clean install
```

3. Deploy to a Jakarta EE compatible server (e.g., Apache Tomcat 10+):
   - Copy `target/nextcart.war` to Tomcat's `webapps/` directory
   - Start Tomcat

The backend API will be available at: `http://localhost:8080/nextcart/api`

### Frontend Setup

1. Navigate to the frontend directory:
```bash
cd nextcart-frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

The frontend will be available at: `http://localhost:3000`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/slug/{slug}` - Get product by slug
- `GET /api/products/featured` - Get featured products

### Categories
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID
- `GET /api/categories/slug/{slug}` - Get category by slug

### Brands
- `GET /api/brands` - Get all brands
- `GET /api/brands/{id}` - Get brand by ID
- `GET /api/brands/slug/{slug}` - Get brand by slug

## Database Schema

The database includes the following tables:
- `users` - Customer information
- `admins` - Admin users
- `products` - Product catalog
- `product_images` - Product images
- `product_variants` - Product variants (size, color, etc.)
- `categories` - Product categories
- `brands` - Product brands
- `carts` - Shopping carts
- `cart_items` - Cart items
- `wishlists` - Wishlists
- `orders` - Orders
- `order_items` - Order items
- `payments` - Payment records
- `coupons` - Discount coupons
- `reviews` - Product reviews
- `hero_banners` - Home page hero banners
- `home_sections` - Home page sections
- `notifications` - User notifications

## Features

### Customer Features
- ✅ User registration and login
- ✅ Browse products with search and filters
- ✅ Product details with reviews
- ✅ Shopping cart management
- ✅ Wishlist functionality
- ✅ Checkout process
- ✅ Order history and tracking
- ✅ User profile management

### Admin Features
- ✅ Dashboard with analytics
- ✅ Product management (CRUD)
- ✅ Category management
- ✅ Order management
- ✅ Customer management
- ✅ Banner management
- ✅ Coupon management
- ✅ Reports and analytics

## Development

### Backend Development
The backend follows a clean 3-tier architecture:
- **Controller Layer** - REST API endpoints
- **Service Layer** - Business logic
- **DAO Layer** - Data access objects

### Frontend Development
The frontend uses a component-based architecture with:
- Reusable React components
- Type-safe TypeScript interfaces
- Responsive design with Tailwind CSS
- Client-side state management

## License

MIT
