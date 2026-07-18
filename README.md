<div align="center">

# 🛍️ NEXTCART - Full Stack AI-Powered E-Commerce Platform

A complete, production-ready full-stack e-commerce platform built with modern technologies. Features AI-powered recommendations, AI chatbot, voice assistant, fraud detection, and much more!

[![Next.js](https://img.shields.io/badge/Next.js-14-black?style=for-the-badge&logo=next.js&logoColor=white)](https://nextjs.org/)
[![Java](https://img.shields.io/badge/Java-17-red?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8-blue?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind-3.4-38bdf8?style=for-the-badge&logo=tailwind-css&logoColor=white)](https://tailwindcss.com/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.5-blue?style=for-the-badge&logo=typescript&logoColor=white)](https://www.typescriptlang.org/)

</div>

---

## 📋 Table of Contents

- [Project Overview](#-project-overview)
- [Tech Stack](#-tech-stack)
- [Key Features](#-key-features)
- [Feature Gallery](#-feature-gallery)
- [Advantages](#-advantages)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Getting Started](#-getting-started)
  - [Database Setup](#1-database-setup)
  - [Backend Setup](#2-backend-setup)
  - [Next.js Frontend Setup](#3-nextjs-frontend-setup)
  - [HTML Templates](#4-html-templates)
- [Running the Application](#-running-the-application)
- [Test Credentials](#-test-credentials)
- [API Endpoints](#-api-endpoints)
- [License](#-license)

---

## 📖 Project Overview

**NEXTCART** is a feature-rich, production-grade e-commerce platform designed to provide a seamless shopping experience for customers and powerful management tools for administrators. It includes both customer-facing features (like browsing products, cart, checkout) and comprehensive admin tools (like product, order, inventory, and fraud management). The platform is built with scalability, security, and performance in mind.

---

## 🛠️ Tech Stack

### Frontend
- **HTML5 & CSS3** - Core web technologies
- **Modern UI/UX Templates** - Responsive, feature-rich HTML templates with dark/light mode support
- **Tailwind CSS** (planned for Next.js version) - Utility-first CSS framework

### Backend (Monolith)
- **Core Java 17** - Programming language
- **Jakarta EE 10** - Enterprise platform
- **Jersey 3.1.5** - JAX-RS (RESTful web services)
- **JDBC** - Database connectivity
- **HikariCP 5.0.1** - High-performance connection pooling
- **JWT 0.12.3** - JSON Web Tokens for authentication
- **BCrypt** - Password hashing
- **Jackson 2.15.3** - JSON processing
- **Maven 3.8+** - Build and dependency management
- **Jetty 11** - Embedded web server

### Database
- **MySQL 8** - Relational database

---

## ✨ Key Features

### 🤖 AI-Powered Features
- ✅ **AI Product Recommendation Engine** - Personalized product recommendations based on user behavior
- ✅ **NEXA AI Commerce Assistant** - Intelligent chatbot for customer support
- ✅ **AI Voice & Multilingual Assistant** - Voice commands and multi-language support
- ✅ **Fraud Detection & Rules Manager** - AI-powered fraud detection and prevention system
- ✅ **Price History Analysis** - Smart pricing insights and comparisons
- ✅ **Analytics & Insights** - AI-driven business intelligence dashboard

### 👤 Customer Features
- ✅ User registration and login (with email/mobile verification)
- ✅ Browse products with categories and subcategories
- ✅ Advanced search and filtering
- ✅ Product details with images, variants, reviews, and ratings
- ✅ Shopping cart management
- ✅ Wishlist functionality
- ✅ Order history and tracking
- ✅ User profile management
- ✅ Address book management
- ✅ Coupon/discount code support
- ✅ Product exchange and return requests
- ✅ Wallet integration
- ✅ Multiple payment options (UPI, card, COD)
- ✅ 3D secure payment process
- ✅ Order success and tracking
- ✅ Dark and light mode support

### 🛡️ Admin Features
- ✅ Comprehensive admin dashboard with analytics
- ✅ Product management (CRUD operations with variants and images)
- ✅ Category and subcategory management
- ✅ Order management and fulfillment
- ✅ Banner and hero banner management
- ✅ Coupon, flash sale, and featured collection management
- ✅ Customer and user management
- ✅ Role-based access control (RBAC)
- ✅ Fraud review hub
- ✅ Logistics, pincode, and courier management
- ✅ AI recommendation engine monitor
- ✅ Audit logs and system settings
- ✅ Content management (CMS pages, testimonials, popups, announcement bar)
- ✅ Inventory and warehouse management
- ✅ Support ticket management

---

## 📸 Feature Gallery

Here are some screenshots of the NEXTCART platform in action!

### Customer Features

#### 🏠 Home Page
![NEXTCART Home Page](project%20screenshots/Screenshot%202026-07-17%20204620.jpg)

#### 🔐 Customer Login & Registration
![Customer Login](project%20screenshots/login%20lapmp%20.jpg)

#### 🛒 Shopping Cart
![Shopping Cart](project%20screenshots/Screenshot%202026-07-17%20210649.jpg)

#### 📦 Product Details
![Product Details](project%20screenshots/Screenshot%202026-07-17%20210218.jpg)

#### 💳 Payment (UPI & Card)
![Payment Page](project%20screenshots/Screenshot%202026-07-17%20203101.jpg)

---

### Admin Features

#### 📊 Admin Dashboard
![Admin Dashboard](project%20screenshots/Screenshot%202026-07-17%20200534.jpg)

#### 📦 Product Management
![Product Management](project%20screenshots/Screenshot%202026-07-17%20204328.jpg)

#### 📂 Category Management
![Category Management](project%20screenshots/Screenshot%202026-07-17%20195119.jpg)

#### 📋 Order Management
![Order Management](project%20screenshots/Screenshot%202026-07-17%20210725.jpg)

#### 🎨 Banner & Hero Banner Management
![Banner Management](project%20screenshots/Screenshot%202026-07-17%20205828.jpg)

#### 🛡️ Fraud Detection & Rules Manager
![Fraud Detection](project%20screenshots/Screenshot%202026-07-17%20211438.jpg)

#### 🤖 AI Recommendation Engine Monitor
![AI Recommendations](project%20screenshots/Screenshot%202026-07-17%20211114.jpg)

---

## 🎯 Advantages

1. **Production-Ready Architecture** - Clean, scalable 3-tier architecture
2. **Security** - JWT-based authentication, BCrypt password hashing, CORS protection
3. **Performance** - HikariCP connection pooling, optimized database queries
4. **Responsive Design** - Mobile-first, responsive templates
5. **Feature-Rich** - Comprehensive set of features for both customers and admins
6. **Extensible** - Modular design for easy feature additions
7. **Modern UI/UX** - Beautiful, intuitive interface with dark/light mode
8. **Fraud Prevention** - Built-in fraud detection system
9. **Comprehensive Documentation** - Well-structured README and codebase

---

## 📁 Project Structure

```
NEXTCART E-COMMERCE/
├── nextcart-frontend/          # Next.js + React + TypeScript Frontend (App Router)
│   ├── app/                    # Next.js App Router pages
│   ├── components/             # Reusable React components
│   ├── context/                # React Context (Auth, Cart, Wishlist)
│   ├── lib/                    # API utilities
│   ├── types/                  # TypeScript type definitions
│   └── package.json
├── NEXTCART FRONTEND/          # HTML templates (customer & admin UI prototypes)
├── nextcart-backend/           # Java Monolith Backend
│   └── src/main/java/com/nextcart/
│       ├── config/             # Configuration (Database, Application)
│       ├── controller/         # REST API Controllers
│       ├── service/            # Business Logic Interfaces
│       ├── serviceImpl/        # Service Implementations
│       ├── dao/                # Data Access Object Interfaces
│       ├── daoImpl/            # DAO Implementations
│       ├── model/              # Entity Models
│       ├── dto/                # Data Transfer Objects
│       ├── exception/          # Exception Handling
│       ├── filter/             # CORS, Authorization, Logging Filters
│       ├── security/           # JWT Authentication
│       ├── util/               # Utility Classes
│       └── constants/          # Application Constants
├── nextcart-microservices/     # Spring Boot Microservices (scalable backend)
├── database/                   # MySQL Schema & Seed Data
│   ├── nextcart_db_schema.sql  # Database Schema
│   └── insert_users.sql        # Test Users
├── project screenshots/        # Feature screenshots
└── README.md                   # This file
```

---

## 📋 Prerequisites

Make sure you have the following installed on your system:

- **Node.js 18+** (for future Next.js frontend)
- **Java 17+** - [Download from Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)
- **Maven 3.8+** - [Download Maven](https://maven.apache.org/download.cgi)
- **MySQL 8+** - [Download MySQL](https://dev.mysql.com/downloads/mysql/)
- **Git** (optional, for version control)

---

## 🚀 Getting Started

### 1. Database Setup

1. **Start MySQL Server** - Ensure your MySQL server is running.

2. **Create Database & Import Schema** - Run the database schema script:
   ```bash
   mysql -u root -p < database/nextcart_db_schema.sql
   ```

   This will:
   - Create a database named `nextcart_db`
   - Create all necessary tables
   - Insert sample data (categories, products, coupons, etc.)

3. **Verify Database Connection** - Update the `config.properties` file (located at `nextcart-backend/src/main/resources/config.properties`) with your MySQL credentials:
   ```properties
   db.url=jdbc:mysql://localhost:3306/nextcart_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
   db.username=root  # your MySQL username
   db.password=root123  # your MySQL password
   ```

### 2. Backend Setup

1. **Navigate to Backend Directory**
   ```bash
   cd nextcart-backend
   ```

2. **Build the Project**
   ```bash
   mvn clean install
   ```

3. **Run the Backend Server**
   ```bash
   mvn jetty:run
   ```

   The backend API will be available at: `http://localhost:8080/api/v1/`

### 3. Next.js Frontend Setup

1. **Navigate to Frontend Directory**
   ```bash
   cd nextcart-frontend
   ```

2. **Install Dependencies**
   ```bash
   npm install
   ```

3. **Run the Frontend Server**
   ```bash
   npm run dev
   ```

   The frontend will be available at: `http://localhost:3000`

### 4. HTML Templates

The UI prototypes are available as static HTML templates in the `NEXTCART FRONTEND/` directory. You can open any HTML file in your browser to see the UI.

---

## ▶️ Running the Application

### Starting Backend

1. Open a terminal/command prompt
2. Navigate to the backend directory:
   ```bash
   cd "c:\Users\shaik\Downloads\NEXTCART E-COMMERCE\nextcart-backend"
   ```
3. Run the server:
   ```bash
   mvn jetty:run
   ```

Wait for the server to start. You should see something like:
```
[INFO] Started Jetty Server
```

### Viewing Frontend Templates

Simply open any of the HTML files in the `NEXTCART FRONTEND` folder with your browser. For example:
- `nexcart_desktop_home_v1.html` - Customer home page
- `nexcart_admin_dashboard_dark.html` - Admin dashboard
- `nexcart_customer_login_page.html` - Customer login page

---

## 🔑 Test Credentials

### Admin User
- **Email**: `shaikhismail57@example.com`
- **Username**: `shaikhismail57`
- **Password**: `Shaikh Ismail@200301`

### Customer Users
- **Email**: `john.doe@example.com`
- **Password**: `password123`

And 6 more customer accounts (jane.smith@example.com, bob.johnson@example.com, etc.) with password: `password123`

---

## 📡 API Endpoints

All API endpoints are prefixed with `/api/v1/`

### Authentication
- `POST /auth/register` - Register new user
- `POST /auth/login` - User login

### Products
- `GET /products` - Get all products
- `GET /products/{id}` - Get product by ID
- `POST /products` - Create product (admin only)
- `PUT /products/{id}` - Update product (admin only)
- `DELETE /products/{id}` - Delete product (admin only)

### Categories & Subcategories
- `GET /categories` - Get all categories
- `GET /subcategories` - Get all subcategories

### Carts
- `GET /cart/{userId}` - Get user cart
- `POST /cart/items` - Add item to cart
- `PUT /cart/items/{itemId}` - Update cart item
- `DELETE /cart/items/{itemId}` - Remove item from cart

### Orders
- `GET /orders` - Get all orders (admin)
- `GET /orders/{userId}` - Get user orders
- `POST /orders` - Place new order
- `PUT /orders/{id}/status` - Update order status (admin)

### Admin Endpoints
- `GET /admin/dashboard` - Admin dashboard stats
- `GET /banners` - Manage banners
- `GET /hero-banners` - Manage hero banners
- `GET /coupons` - Manage coupons
- And many more...

---

## 📄 License

MIT License - see [LICENSE](LICENSE) file for details (if available)
