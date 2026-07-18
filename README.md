<div align="center">

# 🛍️ NEXTCART - Full Stack AI-Powered E-Commerce Platform

A complete, production-ready full-stack e-commerce platform built with modern technologies. Features AI-powered recommendations, AI chatbot, voice assistant, fraud detection, and much more!

[![Next.js](https://img.shields.io/badge/Next.js-14-black?style=for-the-badge&logo=next.js&logoColor=white)](https://nextjs.org/)
[![React](https://img.shields.io/badge/React-18-61DAFB?style=for-the-badge&logo=react&logoColor=white)](https://react.dev/)
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
- **React 18** - UI library for building interactive user interfaces
- **Next.js 14** - React framework with App Router for production-grade applications
- **TypeScript** - Typed JavaScript for better developer experience and fewer bugs
- **Tailwind CSS** - Utility-first CSS framework for rapid UI development
- **HTML5 & CSS3** - Core web technologies
- **Modern UI/UX Templates** - Responsive, feature-rich HTML templates with dark/light mode support

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

Here are all 111 screenshots of the NEXTCART platform organized by category! Screenshots are displayed in a responsive grid layout for easy viewing!

---

### 🏠 Home Page & Landing Screens

<div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 15px;">
  <img src="project screenshots/_homepage_dark_mode2.jpg" alt="Homepage Dark Mode 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/homepage_dark_mode.jpg" alt="Homepage Dark Mode" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/homepage_dark_mode3.jpg" alt="Homepage Dark Mode 3" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nexcart_customer_home_page.jpg" alt="NEXTCART Customer Home Page" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nexcart_customer_home_page1.jpg" alt="NEXTCART Customer Home Page 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nexcart_desktop_home_v1.jpg" alt="NEXTCART Desktop Home V1" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop desktop home v2.jpg" alt="Desktop Home V2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/mobile nexcart_home_screen_v1_4.jpg" alt="Mobile Home Screen V1.4" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nexcart_home_screen_v1_5.jpg" alt="Home Screen V1.5" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nextcart mobile home dark .jpg" alt="Nextcart Mobile Home Dark" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
</div>

---

### 🔐 Authentication & Account Management

<div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 15px;">
  <img src="project screenshots/Login Lamp login and Registration.jpg" alt="Login & Registration" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nexcart_customer_login_page.jpg" alt="Customer Login" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/forgot_password_success.jpg" alt="Forgot Password Success" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/set_new_password_screen.jpg" alt="Set New Password" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/secure_cod_otp_verification.jpg" alt="Secure COD OTP Verification" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nexcart_customer_profile_dark.jpg" alt="Customer Profile Dark" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/add_address_(dark_mode).jpg" alt="Add Address Dark Mode" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
</div>

---

### 🔍 Product Browsing & Details

<div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 15px;">
  <img src="project screenshots/Audio Accessories and hub.jpg" alt="Audio & Accessories Hub" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/Audio Accessories and hub 2.jpg" alt="Audio & Accessories Hub 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/gaming_&_music_entertainment_hub.jpg" alt="Gaming & Music Entertainment Hub" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/gaming_&_music_entertainment_hub2.jpg" alt="Gaming & Music Entertainment Hub 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/grocery_availability_logic_ui_logic_&_checker.jpg" alt="Grocery Availability Logic" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/home_appliances_dark_gallery_dark.jpg" alt="Home Appliances Gallery Dark" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/home_appliances_dark_gallery_dark1.jpg" alt="Home Appliances Gallery Dark 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/lifestyle and living dark theme.jpg" alt="Lifestyle & Living Dark Theme" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nexcart_desktop_category_listing.jpg" alt="Category Listing Desktop" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nexcart_desktop_category_listing2.jpg" alt="Category Listing Desktop 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nexcart_product_detail_page.jpg" alt="Product Detail Page" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nexcart_product_detail_page1.jpg" alt="Product Detail Page 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/mobile product_details_screen_.jpg" alt="Mobile Product Details" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/mobile product_details_screen_2.jpg" alt="Mobile Product Details 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/mobile_product_page_with_exchange_wizard.jpg" alt="Mobile Exchange Wizard" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/mobile_product_page_with_exchange_wizard2.jpg" alt="Mobile Exchange Wizard 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/product exchnage flow dark.jpg" alt="Product Exchange Flow Dark" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/product exchnage flow dark2 .jpg" alt="Product Exchange Flow Dark 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/size_chart_(dark_mode).jpg" alt="Size Chart Dark" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/size_chart_(dark_mode)1.jpg" alt="Size Chart Dark 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/sports,_auto_&_industrial_hub.jpg" alt="Sports & Industrial Hub" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/sports,_auto_&_industrial_hub2.jpg" alt="Sports & Industrial Hub 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/sports,_auto_&_industrial_hub3.jpg" alt="Sports & Industrial Hub 3" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/toys and school supplies.jpg" alt="Toys & School Supplies" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/toys and school supplies2.jpg" alt="Toys & School Supplies 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/toys and school supplies3.jpg" alt="Toys & School Supplies 3" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/tv_&_appliances_superstore_hub.jpg" alt="TV & Appliances Hub" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/tv_&_appliances_superstore_hub1.jpg" alt="TV & Appliances Hub 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/tv_&_appliances_superstore_hub2.jpg" alt="TV & Appliances Hub 3" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/tv_&_appliances_superstore_hub3.jpg" alt="TV & Appliances Hub 4" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/tv_&_home_entertainment_flagship_store.jpg" alt="Home Entertainment Flagship" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/tv_&_home_entertainment_flagship_store1.jpg" alt="Home Entertainment Flagship 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop_advanced_search_results_dark.jpg" alt="Advanced Search Results" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
</div>

---

### 🛒 Shopping Cart & Checkout

<div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 15px;">
  <img src="project screenshots/cart_with_exchange_breakdown.jpg" alt="Cart with Exchange Breakdown" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nextcart shopping cart page.jpg" alt="Shopping Cart" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop my wallet dark.jpg" alt="My Wallet Dark" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop 3d card payment.jpg" alt="3D Card Payment" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop_payment_&_upi_dark.jpg" alt="Payment & UPI Dark" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop_payment_&_upi_light.jpg" alt="Payment & UPI Light" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop razropay checkout.jpg" alt="Razorpay Checkout" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/payment_finalizing_transition.jpg" alt="Payment Finalizing" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/payment_success_receipt_animation.jpg" alt="Payment Success Receipt" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/order_success_screen.jpg" alt="Order Success" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/order_success_screen_2.jpg" alt="Order Success 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop_api_&_service_explorer.jpg" alt="API & Service Explorer" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop_api_&_service_explorer1.jpg" alt="API & Service Explorer 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
</div>

---

### 📦 Orders & Delivery

<div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 15px;">
  <img src="project screenshots/desktop my exchange requests tracker.jpg" alt="Exchange Requests Tracker" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop_order_cancellation_flow.jpg" alt="Order Cancellation Flow" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop_order_cancellation_flow1.jpg" alt="Order Cancellation Flow 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop return step light .jpg" alt="Return Step Light" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop return step light 2.jpg" alt="Return Step Light 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop_live_tracking_map_light.jpg" alt="Live Tracking Map" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/logistics_&_courier_performance_hub.jpg" alt="Logistics Performance Hub" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/logistics_&_courier_performance_hub1.jpg" alt="Logistics Performance Hub 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/shipment_fulfillment_&_awb_label.jpg" alt="Shipment Fulfillment AWB" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nextcart minutes delivery hub.jpg" alt="Minutes Delivery Hub" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nextcart minutes delivery hub2.jpg" alt="Minutes Delivery Hub 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
</div>

---

### 🤖 Customer Support & AI Help

<div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 15px;">
  <img src="project screenshots/desktop help center and AI Chatbot .jpg" alt="Help Center & AI Chatbot" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop help center and AI Chatbot 2 .jpg" alt="Help Center & AI Chatbot 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nexcart_help_center_home.jpg" alt="Help Center Home" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nexcart_help_center_home1.jpg" alt="Help Center Home 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nexa_ai_commerce_assistant_hub.jpg" alt="NEXA AI Commerce Assistant" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nexa_ai_voice_&_multilingual_assistant.jpg" alt="AI Voice & Multilingual" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop_price_history_analysis_dark_theme.jpg" alt="Price History Analysis" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop_price_history_analysis_dark_theme2.jpg" alt="Price History Analysis 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
</div>

---

### 🏢 Admin Dashboard

<div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 15px;">
  <img src="project screenshots/nextcart admin login hub .jpg" alt="Admin Login Hub" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop admin dashboard.jpg" alt="Admin Dashboard" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop admin dashboard2.jpg" alt="Admin Dashboard 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nexcart_admin_dashboard_overview.jpg" alt="Admin Dashboard Overview" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nexcart_admin_dashboard_overview1.jpg" alt="Admin Dashboard Overview 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nexcart_admin_master_dashboard.jpg" alt="Master Dashboard" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
</div>

---

### 📦 Admin Product Management

<div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 15px;">
  <img src="project screenshots/admin_add_new_product_form.jpg" alt="Add New Product Form" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/admin_add_new_product_form1.jpg" alt="Add New Product Form 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/add product step 1 light.jpg" alt="Add Product Step 1" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/add product step 1 light 2.jpg" alt="Add Product Step 1 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/admin product master control.jpg" alt="Product Master Control" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/seller_storefront_editor_light.jpg" alt="Seller Storefront Editor" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
</div>

---

### 📋 Admin Order & Logistics

<div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 15px;">
  <img src="project screenshots/nexcart_admin_order_management.jpg" alt="Admin Order Management" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nextcart admin order management.jpg" alt="Admin Order Management 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/admin exchange audit and pickup hub .jpg" alt="Exchange Audit & Pickup Hub" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/admin_exchange_verification_tool.jpg" alt="Exchange Verification Tool" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/admin_exchange_verification_tool1.jpg" alt="Exchange Verification Tool 2" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/admin_pincode_&_logistics_manager_dark_manager.jpg" alt="Pincode & Logistics Manager" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
</div>

---

### 📂 Admin Content & Promotions

<div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 15px;">
  <img src="project screenshots/admin_content_&_hero_editor_dark.jpg" alt="Content & Hero Editor Dark" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/admin_content_&_hero_editor_light.jpg" alt="Content & Hero Editor Light" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nexcart_admin_hero_&_content_editor.jpg" alt="Hero & Content Editor" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/nextcart admin hero editor dark theme.jpg" alt="Hero Editor Dark Theme" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop_flash_sale_manager_light.jpg" alt="Flash Sale Manager" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
</div>

---

### 🛡️ Admin Fraud & Security

<div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 15px;">
  <img src="project screenshots/admin_fraud_control_center.jpg" alt="Fraud Control Center" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/admin_fraud_review_dark.jpg" alt="Fraud Review Dark" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/admin_fraud_&_rules_manager_light.jpg" alt="Fraud & Rules Manager" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop admin security center .jpg" alt="Admin Security Center" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
  <img src="project screenshots/desktop_ai_recommendation_engine_monitor.jpg" alt="AI Recommendation Monitor" style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
</div>


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

- **Node.js 18+** - For Next.js frontend
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
