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

Here are screenshots of the NEXTCART platform in action!

---

### 👤 Customer Features

#### 🏠 Home Page & Theme
![Nextcart Mobile Home Page](project%20screenshots/Nextcart%20mobile%20home%20page.jpg)
![Nextcart Mobile Home Page Dark Theme](project%20screenshots/nextcart%20mobile%20home%20page%20dark%20theme.jpg)
![NEXTCART Customer Home Page](project%20screenshots/nexcart_customer_home_page.jpg)
![NEXTCART Desktop Home V1](project%20screenshots/nexcart_desktop_home_v1.jpg)
![NEXTCART Desktop Home V2](project%20screenshots/nexcart_desktop_home_v2.jpg)
![NEXTCART Home Screen V1 4](project%20screenshots/nexcart_home_screen_v1_4.jpg)
![NEXTCART Home Screen V1 5](project%20screenshots/nexcart_home_screen_v1_5.jpg)
![NEXTCART Mobile Home Dark](project%20screenshots/nexcart_mobile_home_dark.jpg)
![Flipkart Style Homepage Dark Mode](project%20screenshots/flipkart-style_homepage_dark_mode.jpg)

#### 🔐 Authentication & Profile
![Login Lamp](project%20screenshots/login%20lamp.jpg)
![Login Lapmp](project%20screenshots/login%20lapmp%20.jpg)
![NEXTCART Customer Login Page](project%20screenshots/nexcart_customer_login_page.jpg)
![Reset Password](project%20screenshots/Reset%20Password.jpg)
![Set New Password Screen](project%20screenshots/set_new_password_screen.jpg)
![User Profile Page](project%20screenshots/user%20profile%20page.jpg)
![NEXTCART Customer Profile Dark](project%20screenshots/nexcart_customer_profile_dark.jpg)
![Add Address Dark Mode](project%20screenshots/add_address_(dark_mode).jpg)

#### 🛒 Shopping & Cart
![NEXTCART Shopping Cart Page](project%20screenshots/nexcart_shopping_cart_page.jpg)
![Cart With Exchange Breakdown](project%20screenshots/cart_with_exchange_breakdown.jpg)
![Desktop My Wallet Dark](project%20screenshots/desktop_my_wallet_dark.jpg)

#### 📦 Product Details & Exchange
![Product Details Page](project%20screenshots/product%20deatils%20page.jpg)
![Project Details Page 2](project%20screenshots/project%20deatails%20page%202.jpg)
![NEXTCART Product Detail Page](project%20screenshots/nexcart_product_detail_page.jpg)
![Product Details Screen 4](project%20screenshots/product_details_screen_4.jpg)
![Mobile Product Page With Exchange Wizard](project%20screenshots/mobile_product_page_with_exchange_wizard.jpg)
![Product Exchange Flow Dark](project%20screenshots/product_exchange_flow_dark.gif)
![Size Chart Dark Mode](project%20screenshots/size_chart_(dark_mode).jpg)
![Clothes Size Guide](project%20screenshots/clothes%20size%20guide.jpg)
![Clothes Size Guide 2](project%20screenshots/clothes%20size%20guide%202.jpg)

#### 🔍 Search & Browse
![Desktop Advanced Search Results Dark](project%20screenshots/desktop_advanced_search_results_dark.jpg)
![NEXTCART Desktop Category Listing](project%20screenshots/nexcart_desktop_category_listing.jpg)
![Audio & Accessories Hub Dark](project%20screenshots/audio_&_accessories_hub_dark.jpg)
![Gaming & Music Entertainment Hub](project%20screenshots/gaming_&_music_entertainment_hub.jpg)
![Home Appliances Dark Gallery Dark](project%20screenshots/home_appliances_dark_gallery_dark.jpg)
![Lifestyle & Living Dark Mode](project%20screenshots/lifestyle_&_living_dark_mode.jpg)
![Sports, Auto & Industrial Hub](project%20screenshots/sports,_auto_&_industrial_hub.jpg)
![Toys & School Supplies Hub](project%20screenshots/toys_&_school_supplies_hub.jpg)
![TV & Appliances Superstore Hub](project%20screenshots/tv_&_appliances_superstore_hub.jpg)
![TV & Home Entertainment Flagship Store](project%20screenshots/tv_&_home_entertainment_flagship_store.jpg)

#### 💰 Payment & Checkout
![Desktop Payment & UPI Dark](project%20screenshots/desktop_payment_&_upi_dark.png)
![Desktop Payment & UPI Light](project%20screenshots/desktop_payment_&_upi_light.jpg)
![Desktop 3D Card Payment Light](project%20screenshots/desktop_3d_card_payment_light.jpg)
![Desktop Razorpay Checkout & Trust Hub](project%20screenshots/desktop_razorpay_checkout_&_trust_hub.jpg)
![Payment Finalizing Transition](project%20screenshots/payment_finalizing_transition.jpg)
![Securing Transaction Animation](project%20screenshots/Securing%20Transaction%20animation.jpg)
![Secure COD OTP Verification](project%20screenshots/secure_cod_otp_verification.jpg)
![Security Verification OTP Page](project%20screenshots/security%20verification%20otp%20page.jpg)
![Payment Receipt](project%20screenshots/payment%20reciept.jpg)
![Payment Success Receipt Animation](project%20screenshots/payment_success_receipt_animation.jpg)
![Order Success Page](project%20screenshots/order%20success%20page.jpg)
![Order Success 2](project%20screenshots/order%20success2.jpg)
![Order Success Screen 2](project%20screenshots/order_success_screen_2.jpg)

#### 📦 Orders & Delivery
![Desktop Order Cancellation Flow](project%20screenshots/desktop_order_cancellation_flow.jpg)
![Desktop Return Step 2 Light](project%20screenshots/desktop_return_step_2_light.jpg)
![Desktop My Exchange Requests Tracker](project%20screenshots/desktop_my_exchange_requests_tracker.jpg)
![Desktop Live Tracking Map Light](project%20screenshots/desktop_live_tracking_map_light.jpg)
![Location Tracker](project%20screenshots/location%20tracker.jpg)
![Select Delivery Zone Page](project%20screenshots/select%20delivery%20zone%20page.jpg)
![NEXTCART Minutes Delivery Hub](project%20screenshots/nexcart_minutes_delivery_hub.jpg)

#### 🤖 AI & Help
![Desktop Help Center & AI Chatbot Dark](project%20screenshots/desktop_help_center_&_ai_chatbot_dark.jpg)
![NEXTCART Help Center Home](project%20screenshots/nexcart_help_center_home.jpg)
![NEXTA AI Commerce Assistant Hub](project%20screenshots/nexa_ai_commerce_assistant_hub.jpg)
![NEXTA AI Voice & Multilingual Assistant](project%20screenshots/nexa_ai_voice_&_multilingual_assistant.jpg)
![Desktop Price History Analysis Dark](project%20screenshots/desktop_price_history_analysis_dark.jpg)
![Grocery Availability Logic UI Logic & Checker](project%20screenshots/grocery_availability_logic_ui_logic_&_checker.jpg)

---

### 🛡️ Admin Features

#### 🏢 Dashboard & Login
![NEXTCART Admin Login Hub](project%20screenshots/nexcart_admin_login_hub.jpg)
![NEXTCART Admin Dashboard Dark](project%20screenshots/nexcart_admin_dashboard_dark.jpg)
![NEXTCART Admin Dashboard Overview](project%20screenshots/nexcart_admin_dashboard_overview.jpg)
![NEXTCART Admin Master Dashboard](project%20screenshots/nexcart_admin_master_dashboard.jpg)

#### 📦 Product Management
![Product Admin](project%20screenshots/product-admin.jpg)
![Admin Add New Product Form](project%20screenshots/admin_add_new_product_form.jpg)
![Admin Product Master Control](project%20screenshots/admin_product_master_control.jpg)
![Add Product Step 1 Light 2](project%20screenshots/add_product_step_1_light_2.jpg)
![Seller Storefront Editor Light](project%20screenshots/seller_storefront_editor_light.jpg)

#### 📂 Category & Banner Management
![Category Admin](project%20screenshots/category-admin.jpg)
![Desktop Admin Category Manager Dark](project%20screenshots/desktop_admin_category_manager_dark.jpg)
![Desktop Admin Category Manager Light](project%20screenshots/desktop_admin_category_manager_light.jpg)
![Hero Banner Admin](project%20screenshots/hero-banner-admin.jpg)
![Admin Content & Hero Editor Dark](project%20screenshots/admin_content_&_hero_editor_dark.jpg)
![Admin Content & Hero Editor Light](project%20screenshots/admin_content_&_hero_editor_light.jpg)
![NEXTCART Admin Hero & Content Editor](project%20screenshots/nexcart_admin_hero_&_content_editor.jpg)
![NEXTCART Admin Hero Editor Dark](project%20screenshots/nexcart_admin_hero_editor_dark.jpg)
![Desktop Flash Sale Manager Light](project%20screenshots/desktop_flash_sale_manager_light.jpg)

#### 📋 Order & Logistics Management
![NEXTCART Admin Order Management](project%20screenshots/nexcart_admin_order_management.jpg)
![NEXTCART Admin Order Manager](project%20screenshots/nexcart_admin_order_manager.jpg)
![Admin Exchange Audit & Pickup Hub](project%20screenshots/admin_exchange_audit_&_pickup_hub.jpg)
![Admin Exchange Verification Tool](project%20screenshots/admin_exchange_verification_tool.jpg)
![Logistics & Courier Performance Hub](project%20screenshots/logistics_&_courier_performance_hub.jpg)
![Admin Pincode & Logistics Manager Dark Manager](project%20screenshots/admin_pincode_&_logistics_manager_dark_manager.jpg)
![Shipment Fulfillment & AWB Label](project%20screenshots/shipment_fulfillment_&_awb_label.jpg)

#### 🛡️ Fraud & Security Management
![Admin Fraud Control Center](project%20screenshots/admin_fraud_control_center.jpg)
![Admin Fraud Review Hub Dark](project%20screenshots/admin_fraud_review_hub_dark.jpg)
![Admin Fraud & Rules Manager Light](project%20screenshots/admin_fraud_&_rules_manager_light.jpg)
![Desktop Admin Security Center Dark](project%20screenshots/desktop_admin_security_center_dark.jpg)

#### 🤖 AI & Advanced Features
![Desktop AI Recommendation Engine Monitor](project%20screenshots/desktop_ai_recommendation_engine_monitor.jpg)
![Desktop API & Service Explorer](project%20screenshots/desktop_api_&_service_explorer.jpg)

---

### 📦 Extra Screenshots
![Extra 01](project%20screenshots/extra_01.jpg)
![Extra 02](project%20screenshots/extra_02.jpg)
![Extra 03](project%20screenshots/extra_03.jpg)
![Extra 04](project%20screenshots/extra_04.jpg)
![Extra 05](project%20screenshots/extra_05.jpg)
![Extra 06](project%20screenshots/extra_06.jpg)
![Extra 07](project%20screenshots/extra_07.jpg)
![Extra 08](project%20screenshots/extra_08.jpg)
![Extra 09](project%20screenshots/extra_09.jpg)
![Extra 10](project%20screenshots/extra_10.jpg)
![Extra 11](project%20screenshots/extra_11.jpg)
![Extra 12](project%20screenshots/extra_12.jpg)
![Extra 13](project%20screenshots/extra_13.jpg)
![Extra 14](project%20screenshots/extra_14.jpg)
![Extra 15](project%20screenshots/extra_15.jpg)
![Extra 16](project%20screenshots/extra_16.jpg)
![Extra 17](project%20screenshots/extra_17.jpg)
![Extra 18](project%20screenshots/extra_18.jpg)
![Extra 19](project%20screenshots/extra_19.jpg)
![Extra 20](project%20screenshots/extra_20.jpg)
![Extra 21](project%20screenshots/extra_21.jpg)


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
