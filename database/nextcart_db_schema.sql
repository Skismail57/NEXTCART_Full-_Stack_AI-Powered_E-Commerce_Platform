-- NEXTCART - Production-Grade E-Commerce Database Schema
-- Version: 2.0
-- Database: nextcart_db

-- Create database
CREATE DATABASE IF NOT EXISTS nextcart_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE nextcart_db;

-- ====================================================
-- 1. ROLES
-- ====================================================
CREATE TABLE IF NOT EXISTS roles (
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert default roles
INSERT INTO roles (role_name, description) VALUES
('CUSTOMER', 'Regular customer'),
('ADMIN', 'Administrator with full access'),
('MANAGER', 'Store manager with limited access');

-- ====================================================
-- 2. USERS
-- ====================================================
CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    role_id INT NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    mobile VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    profile_image VARCHAR(500),
    gender ENUM('MALE', 'FEMALE', 'OTHER'),
    date_of_birth DATE,
    status ENUM('ACTIVE', 'INACTIVE', 'BLOCKED') DEFAULT 'ACTIVE',
    email_verified BOOLEAN DEFAULT FALSE,
    mobile_verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE,
    INDEX idx_email (email),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 3. USER_ADDRESSES
-- ====================================================
CREATE TABLE IF NOT EXISTS user_addresses (
    address_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    full_name VARCHAR(200) NOT NULL,
    mobile VARCHAR(20) NOT NULL,
    address_line1 VARCHAR(500) NOT NULL,
    address_line2 VARCHAR(500),
    landmark VARCHAR(200),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100),
    country VARCHAR(100) NOT NULL DEFAULT 'INDIA',
    postal_code VARCHAR(20) NOT NULL,
    address_type ENUM('HOME', 'WORK', 'OTHER') DEFAULT 'HOME',
    is_default BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 4. OTP_VERIFICATION
-- ====================================================
CREATE TABLE IF NOT EXISTS otp_verification (
    otp_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    otp VARCHAR(10) NOT NULL,
    purpose ENUM('EMAIL_VERIFY', 'MOBILE_VERIFY', 'PASSWORD_RESET') NOT NULL,
    expiry_time TIMESTAMP NOT NULL,
    verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 5. CATEGORIES
-- ====================================================
CREATE TABLE IF NOT EXISTS categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    slug VARCHAR(200) NOT NULL UNIQUE,
    image VARCHAR(500),
    description TEXT,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_slug (slug),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 6. SUB_CATEGORIES
-- ====================================================
CREATE TABLE IF NOT EXISTS sub_categories (
    sub_category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_id INT NOT NULL,
    name VARCHAR(200) NOT NULL,
    slug VARCHAR(200) NOT NULL UNIQUE,
    image VARCHAR(500),
    description TEXT,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE CASCADE,
    INDEX idx_category_id (category_id),
    INDEX idx_slug (slug),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 7. BRANDS
-- ====================================================
CREATE TABLE IF NOT EXISTS brands (
    brand_id INT PRIMARY KEY AUTO_INCREMENT,
    brand_name VARCHAR(200) NOT NULL UNIQUE,
    brand_logo VARCHAR(500),
    description TEXT,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 8. PRODUCTS
-- ====================================================
CREATE TABLE IF NOT EXISTS products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    category_id INT NOT NULL,
    sub_category_id INT,
    brand_id INT,
    product_name VARCHAR(500) NOT NULL,
    slug VARCHAR(500) NOT NULL UNIQUE,
    sku VARCHAR(100) UNIQUE,
    short_description VARCHAR(1000),
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    discount_price DECIMAL(10,2),
    tax DECIMAL(5,2) DEFAULT 0.00,
    stock INT NOT NULL DEFAULT 0,
    weight DECIMAL(8,2),
    warranty VARCHAR(100),
    status ENUM('ACTIVE', 'INACTIVE', 'OUT_OF_STOCK') DEFAULT 'ACTIVE',
    featured BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE CASCADE,
    FOREIGN KEY (sub_category_id) REFERENCES sub_categories(sub_category_id) ON DELETE SET NULL,
    FOREIGN KEY (brand_id) REFERENCES brands(brand_id) ON DELETE SET NULL,
    INDEX idx_slug (slug),
    INDEX idx_category_id (category_id),
    INDEX idx_brand_id (brand_id),
    INDEX idx_status (status),
    INDEX idx_featured (featured)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 9. PRODUCT_IMAGES
-- ====================================================
CREATE TABLE IF NOT EXISTS product_images (
    image_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    display_order INT DEFAULT 0,
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    INDEX idx_product_id (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 10. PRODUCT_VARIANTS
-- ====================================================
CREATE TABLE IF NOT EXISTS product_variants (
    variant_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    color VARCHAR(50),
    size VARCHAR(50),
    ram VARCHAR(50),
    storage VARCHAR(50),
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    sku VARCHAR(100),
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    INDEX idx_product_id (product_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 11. PRODUCT_REVIEWS
-- ====================================================
CREATE TABLE IF NOT EXISTS product_reviews (
    review_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    user_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    title VARCHAR(255),
    review TEXT,
    review_image VARCHAR(500),
    approved BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_product_id (product_id),
    INDEX idx_user_id (user_id),
    INDEX idx_rating (rating)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 12. WISHLIST
-- ====================================================
CREATE TABLE IF NOT EXISTS wishlist (
    wishlist_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    UNIQUE KEY uk_wishlist_user_product (user_id, product_id),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 13. CART
-- ====================================================
CREATE TABLE IF NOT EXISTS cart (
    cart_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 14. CART_ITEMS
-- ====================================================
CREATE TABLE IF NOT EXISTS cart_items (
    cart_item_id INT PRIMARY KEY AUTO_INCREMENT,
    cart_id INT NOT NULL,
    product_id INT NOT NULL,
    variant_id INT,
    quantity INT NOT NULL DEFAULT 1,
    price DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES cart(cart_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    FOREIGN KEY (variant_id) REFERENCES product_variants(variant_id) ON DELETE SET NULL,
    INDEX idx_cart_id (cart_id),
    INDEX idx_product_id (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 15. COUPONS
-- ====================================================
CREATE TABLE IF NOT EXISTS coupons (
    coupon_id INT PRIMARY KEY AUTO_INCREMENT,
    coupon_code VARCHAR(50) NOT NULL UNIQUE,
    discount_type ENUM('PERCENTAGE', 'FIXED') NOT NULL,
    discount_value DECIMAL(10,2) NOT NULL,
    minimum_amount DECIMAL(10,2),
    maximum_discount DECIMAL(10,2),
    expiry_date DATE NOT NULL,
    usage_limit INT,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_coupon_code (coupon_code),
    INDEX idx_status (status),
    INDEX idx_expiry_date (expiry_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 16. ORDERS
-- ====================================================
CREATE TABLE IF NOT EXISTS orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    order_number VARCHAR(50) NOT NULL UNIQUE,
    user_id INT NOT NULL,
    address_id INT NOT NULL,
    coupon_id INT,
    subtotal DECIMAL(10,2) NOT NULL,
    discount DECIMAL(10,2) DEFAULT 0.00,
    tax DECIMAL(10,2) DEFAULT 0.00,
    shipping_charge DECIMAL(10,2) DEFAULT 0.00,
    grand_total DECIMAL(10,2) NOT NULL,
    payment_status ENUM('PENDING', 'PAID', 'FAILED', 'REFUNDED') DEFAULT 'PENDING',
    order_status ENUM('PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED', 'RETURNED', 'REFUNDED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (address_id) REFERENCES user_addresses(address_id) ON DELETE CASCADE,
    FOREIGN KEY (coupon_id) REFERENCES coupons(coupon_id) ON DELETE SET NULL,
    INDEX idx_order_number (order_number),
    INDEX idx_user_id (user_id),
    INDEX idx_order_status (order_status),
    INDEX idx_payment_status (payment_status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 17. ORDER_ITEMS
-- ====================================================
CREATE TABLE IF NOT EXISTS order_items (
    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    variant_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE SET NULL,
    FOREIGN KEY (variant_id) REFERENCES product_variants(variant_id) ON DELETE SET NULL,
    INDEX idx_order_id (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 18. PAYMENTS
-- ====================================================
CREATE TABLE IF NOT EXISTS payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    payment_method VARCHAR(100) NOT NULL,
    transaction_id VARCHAR(255),
    payment_gateway VARCHAR(100),
    payment_status ENUM('PENDING', 'SUCCESS', 'FAILED', 'REFUNDED') DEFAULT 'PENDING',
    amount DECIMAL(10,2) NOT NULL,
    paid_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    INDEX idx_order_id (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 19. ORDER_TRACKING
-- ====================================================
CREATE TABLE IF NOT EXISTS order_tracking (
    tracking_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    status VARCHAR(100) NOT NULL,
    remarks TEXT,
    updated_by INT,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    INDEX idx_order_id (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 20. BANNERS
-- ====================================================
CREATE TABLE IF NOT EXISTS banners (
    banner_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(500),
    image_url VARCHAR(500),
    redirect_url VARCHAR(500),
    display_order INT DEFAULT 0,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 21. OFFERS
-- ====================================================
CREATE TABLE IF NOT EXISTS offers (
    offer_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(500) NOT NULL,
    discount_percentage DECIMAL(5,2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    INDEX idx_status (status),
    INDEX idx_start_date (start_date),
    INDEX idx_end_date (end_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 22. NOTIFICATIONS
-- ====================================================
CREATE TABLE IF NOT EXISTS notifications (
    notification_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    notification_type VARCHAR(50) NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_is_read (is_read)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 23. INVENTORY
-- ====================================================
CREATE TABLE IF NOT EXISTS inventory (
    inventory_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    warehouse VARCHAR(200),
    available_stock INT NOT NULL DEFAULT 0,
    reserved_stock INT NOT NULL DEFAULT 0,
    damaged_stock INT NOT NULL DEFAULT 0,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    INDEX idx_product_id (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 24. EXCHANGE_REQUESTS
-- ====================================================
CREATE TABLE IF NOT EXISTS exchange_requests (
    exchange_id INT PRIMARY KEY AUTO_INCREMENT,
    order_item_id INT NOT NULL,
    reason TEXT NOT NULL,
    remarks TEXT,
    status ENUM('PENDING', 'APPROVED', 'REJECTED', 'COMPLETED') DEFAULT 'PENDING',
    approved_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_item_id) REFERENCES order_items(order_item_id) ON DELETE CASCADE,
    INDEX idx_order_item_id (order_item_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 25. FRAUD_LOGS
-- ====================================================
CREATE TABLE IF NOT EXISTS fraud_logs (
    fraud_log_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    order_id INT,
    risk_score INT,
    reason TEXT,
    action_taken TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE SET NULL,
    INDEX idx_user_id (user_id),
    INDEX idx_order_id (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 26. LOGISTICS
-- ====================================================
CREATE TABLE IF NOT EXISTS logistics (
    logistics_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    courier_name VARCHAR(100),
    tracking_number VARCHAR(255),
    estimated_delivery DATE,
    delivery_status ENUM('PENDING', 'IN_TRANSIT', 'DELIVERED', 'RETURNED') DEFAULT 'PENDING',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    INDEX idx_order_id (order_id),
    INDEX idx_tracking_number (tracking_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 27. CMS_PAGES
-- ====================================================
CREATE TABLE IF NOT EXISTS cms_pages (
    page_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(500) NOT NULL,
    slug VARCHAR(500) NOT NULL UNIQUE,
    content TEXT NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_slug (slug),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 28. HERO_BANNERS
-- ====================================================
CREATE TABLE IF NOT EXISTS hero_banners (
    hero_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(500),
    subtitle VARCHAR(500),
    image_url VARCHAR(500),
    button_text VARCHAR(100),
    button_link VARCHAR(500),
    display_order INT DEFAULT 0,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 29. ADMIN_ACTIVITY_LOGS
-- ====================================================
CREATE TABLE IF NOT EXISTS admin_activity_logs (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    admin_id INT NOT NULL,
    activity TEXT NOT NULL,
    ip_address VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (admin_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_admin_id (admin_id),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 30. SYSTEM_SETTINGS
-- ====================================================
CREATE TABLE IF NOT EXISTS system_settings (
    setting_id INT PRIMARY KEY AUTO_INCREMENT,
    setting_key VARCHAR(100) NOT NULL UNIQUE,
    setting_value TEXT,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_setting_key (setting_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 31. HOMEPAGE_SECTIONS
-- ====================================================
CREATE TABLE IF NOT EXISTS homepage_sections (
    section_id INT PRIMARY KEY AUTO_INCREMENT,
    section_name VARCHAR(200) NOT NULL,
    section_type VARCHAR(100) NOT NULL, -- e.g., HERO, CATEGORIES, PRODUCTS, BANNER, BRANDS
    title VARCHAR(500),
    subtitle VARCHAR(500),
    layout VARCHAR(100) DEFAULT 'GRID', -- GRID, CAROUSEL, SLIDER, LIST
    display_order INT DEFAULT 0,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    config TEXT, -- JSON to store additional config (e.g., product count, category IDs, etc.)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_display_order (display_order),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 32. HOMEPAGE_SECTION_PRODUCTS
-- ====================================================
CREATE TABLE IF NOT EXISTS homepage_section_products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    section_id INT NOT NULL,
    product_id INT NOT NULL,
    display_order INT DEFAULT 0,
    FOREIGN KEY (section_id) REFERENCES homepage_sections(section_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    INDEX idx_section_id (section_id),
    INDEX idx_product_id (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 33. HOMEPAGE_SECTION_CATEGORIES
-- ====================================================
CREATE TABLE IF NOT EXISTS homepage_section_categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    section_id INT NOT NULL,
    category_id INT NOT NULL,
    display_order INT DEFAULT 0,
    FOREIGN KEY (section_id) REFERENCES homepage_sections(section_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE CASCADE,
    INDEX idx_section_id (section_id),
    INDEX idx_category_id (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 34. HOMEPAGE_SECTION_BRANDS
-- ====================================================
CREATE TABLE IF NOT EXISTS homepage_section_brands (
    id INT PRIMARY KEY AUTO_INCREMENT,
    section_id INT NOT NULL,
    brand_id INT NOT NULL,
    display_order INT DEFAULT 0,
    FOREIGN KEY (section_id) REFERENCES homepage_sections(section_id) ON DELETE CASCADE,
    FOREIGN KEY (brand_id) REFERENCES brands(brand_id) ON DELETE CASCADE,
    INDEX idx_section_id (section_id),
    INDEX idx_brand_id (brand_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 35. HOMEPAGE_BANNERS
-- ====================================================
CREATE TABLE IF NOT EXISTS homepage_banners (
    banner_id INT PRIMARY KEY AUTO_INCREMENT,
    position VARCHAR(100) NOT NULL, -- TOP, MIDDLE_1, MIDDLE_2, SIDEBAR, BOTTOM, FOOTER
    title VARCHAR(500),
    image_url VARCHAR(500),
    redirect_url VARCHAR(500),
    display_order INT DEFAULT 0,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    start_date TIMESTAMP NULL,
    end_date TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_position (position),
    INDEX idx_status (status),
    INDEX idx_start_date (start_date),
    INDEX idx_end_date (end_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 36. ANNOUNCEMENT_BAR
-- ====================================================
CREATE TABLE IF NOT EXISTS announcement_bar (
    announcement_id INT PRIMARY KEY AUTO_INCREMENT,
    text TEXT NOT NULL,
    link VARCHAR(500),
    background_color VARCHAR(20),
    text_color VARCHAR(20),
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    display_order INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 37. FLASH_SALES
-- ====================================================
CREATE TABLE IF NOT EXISTS flash_sales (
    flash_sale_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(500) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    banner_image VARCHAR(500),
    discount_percentage DECIMAL(5,2) NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE', 'SCHEDULED') DEFAULT 'SCHEDULED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_status (status),
    INDEX idx_start_time (start_time),
    INDEX idx_end_time (end_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 38. FLASH_SALE_PRODUCTS
-- ====================================================
CREATE TABLE IF NOT EXISTS flash_sale_products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    flash_sale_id INT NOT NULL,
    product_id INT NOT NULL,
    display_order INT DEFAULT 0,
    FOREIGN KEY (flash_sale_id) REFERENCES flash_sales(flash_sale_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    INDEX idx_flash_sale_id (flash_sale_id),
    INDEX idx_product_id (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 39. TESTIMONIALS
-- ====================================================
CREATE TABLE IF NOT EXISTS testimonials (
    testimonial_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(200) NOT NULL,
    customer_avatar VARCHAR(500),
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    text TEXT NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    display_order INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 40. FEATURED_COLLECTIONS
-- ====================================================
CREATE TABLE IF NOT EXISTS featured_collections (
    collection_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    slug VARCHAR(200) NOT NULL UNIQUE,
    description TEXT,
    image VARCHAR(500),
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    display_order INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_status (status),
    INDEX idx_slug (slug)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 41. FEATURED_COLLECTION_PRODUCTS
-- ====================================================
CREATE TABLE IF NOT EXISTS featured_collection_products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    collection_id INT NOT NULL,
    product_id INT NOT NULL,
    display_order INT DEFAULT 0,
    FOREIGN KEY (collection_id) REFERENCES featured_collections(collection_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    INDEX idx_collection_id (collection_id),
    INDEX idx_product_id (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 42. POPUPS
-- ====================================================
CREATE TABLE IF NOT EXISTS popups (
    popup_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    title VARCHAR(500),
    content TEXT,
    image VARCHAR(500),
    button_text VARCHAR(200),
    button_link VARCHAR(500),
    status ENUM('ACTIVE', 'INACTIVE', 'SCHEDULED') DEFAULT 'INACTIVE',
    start_time TIMESTAMP NULL,
    end_time TIMESTAMP NULL,
    display_order INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 21. email_templates
-- ====================================================
CREATE TABLE IF NOT EXISTS email_templates (
    email_template_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL UNIQUE,
    slug VARCHAR(200) NOT NULL UNIQUE,
    subject VARCHAR(500) NOT NULL,
    content TEXT NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_status (status),
    INDEX idx_slug (slug)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 22. permissions
-- ====================================================
CREATE TABLE IF NOT EXISTS permissions (
    permission_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL UNIQUE,
    slug VARCHAR(200) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_slug (slug)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 23. role_permissions
-- ====================================================
CREATE TABLE IF NOT EXISTS role_permissions (
    role_permission_id INT PRIMARY KEY AUTO_INCREMENT,
    role_id INT NOT NULL,
    permission_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(permission_id) ON DELETE CASCADE,
    UNIQUE KEY uk_role_permission (role_id, permission_id),
    INDEX idx_role_id (role_id),
    INDEX idx_permission_id (permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 24. menu_items
-- ====================================================
CREATE TABLE IF NOT EXISTS menu_items (
    menu_item_id INT PRIMARY KEY AUTO_INCREMENT,
    parent_id INT NULL,
    title VARCHAR(200) NOT NULL,
    slug VARCHAR(200),
    url VARCHAR(500),
    icon VARCHAR(500),
    image VARCHAR(500),
    display_order INT DEFAULT 0,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    menu_type ENUM('HEADER', 'FOOTER') DEFAULT 'HEADER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES menu_items(menu_item_id) ON DELETE CASCADE,
    INDEX idx_parent_id (parent_id),
    INDEX idx_status (status),
    INDEX idx_menu_type (menu_type),
    INDEX idx_display_order (display_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 25. audit_logs
-- ====================================================
CREATE TABLE IF NOT EXISTS audit_logs (
    audit_log_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NULL,
    action VARCHAR(255) NOT NULL,
    entity_type VARCHAR(100) NOT NULL,
    entity_id INT NULL,
    old_value TEXT NULL,
    new_value TEXT NULL,
    ip_address VARCHAR(50) NULL,
    user_agent TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_entity_type (entity_type),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 26. support_tickets
-- ====================================================
CREATE TABLE IF NOT EXISTS support_tickets (
    support_ticket_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    assigned_to INT NULL,
    ticket_number VARCHAR(50) NOT NULL UNIQUE,
    subject VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    priority ENUM('LOW', 'MEDIUM', 'HIGH', 'URGENT') DEFAULT 'MEDIUM',
    status ENUM('OPEN', 'IN_PROGRESS', 'RESOLVED', 'CLOSED') DEFAULT 'OPEN',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (assigned_to) REFERENCES users(user_id) ON DELETE SET NULL,
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_priority (priority),
    INDEX idx_ticket_number (ticket_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- 27. ticket_messages
-- ====================================================
CREATE TABLE IF NOT EXISTS ticket_messages (
    ticket_message_id INT PRIMARY KEY AUTO_INCREMENT,
    support_ticket_id INT NOT NULL,
    user_id INT NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (support_ticket_id) REFERENCES support_tickets(support_ticket_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_support_ticket_id (support_ticket_id),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- INSERT SAMPLE DATA
-- ====================================================
-- Default admin user (password: Admin@123, BCrypt hash)
INSERT INTO users (role_id, first_name, last_name, email, mobile, password, status, email_verified) VALUES
(2, 'Admin', 'User', 'admin@nextcart.com', '9876543210', '$2a$10$N9qo8uLOickvxPsP9I.FQeBnF9MqK5FvN5eK5eK5eK5eK5eK5eK5eK5eK', 'ACTIVE', TRUE);

-- Sample categories
INSERT INTO categories (name, slug, description, status) VALUES
('Electronics', 'electronics', 'All electronic gadgets and devices', 'ACTIVE'),
('Fashion', 'fashion', 'Clothing, footwear, and accessories', 'ACTIVE'),
('Home & Kitchen', 'home-kitchen', 'Home appliances and kitchen essentials', 'ACTIVE'),
('Beauty', 'beauty', 'Beauty and personal care products', 'ACTIVE'),
('Sports', 'sports', 'Sports equipment and gear', 'ACTIVE');

-- Sample sub-categories
INSERT INTO sub_categories (category_id, name, slug, description, status) VALUES
(1, 'Smartphones', 'smartphones', 'Latest smartphones', 'ACTIVE'),
(1, 'Laptops', 'laptops', 'Laptops and notebooks', 'ACTIVE'),
(1, 'Audio', 'audio', 'Headphones, speakers, and earbuds', 'ACTIVE'),
(2, 'Men Clothing', 'men-clothing', 'Men fashion', 'ACTIVE'),
(2, 'Women Clothing', 'women-clothing', 'Women fashion', 'ACTIVE');

-- Sample brands
INSERT INTO brands (brand_name, brand_logo, description, status) VALUES
('Apple', 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Apple_logo_black.svg/256px-Apple_logo_black.svg.png', 'Apple Inc.', 'ACTIVE'),
('Samsung', 'https://upload.wikimedia.org/wikipedia/commons/thumb/2/24/Samsung_Logo.svg/256px-Samsung_Logo.svg.png', 'Samsung Electronics', 'ACTIVE'),
('Nike', 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/a6/Logo_NIKE.svg/256px-Logo_NIKE.svg.png', 'Nike Inc.', 'ACTIVE'),
('Sony', 'https://upload.wikimedia.org/wikipedia/commons/thumb/4/4d/Sony_logo.svg/256px-Sony_logo.svg.png', 'Sony Group Corporation', 'ACTIVE');

-- Sample products
INSERT INTO products (category_id, sub_category_id, brand_id, product_name, slug, sku, short_description, description, price, discount_price, tax, stock, status, featured) VALUES
(1, 3, 4, 'Acoustic Pro Headphones', 'acoustic-pro-headphones', 'NC-AUD-001', 'Premium wireless headphones with active noise cancellation', 'High-quality wireless headphones featuring industry-leading active noise cancellation, 30-hour battery life, and premium sound quality.', 299.99, 249.99, 5.00, 50, 'ACTIVE', TRUE),
(1, 1, 1, 'iPhone 15 Pro Max', 'iphone-15-pro-max', 'NC-MOB-001', 'Latest Apple flagship smartphone', 'Apple iPhone 15 Pro Max with A17 Pro chip, titanium design, and advanced camera system.', 1199.99, NULL, 5.00, 25, 'ACTIVE', TRUE),
(2, 4, 3, 'Air Max Running Shoes', 'nike-air-max', 'NC-FSH-001', 'Comfortable running shoes', 'Nike Air Max running shoes for maximum comfort and performance.', 129.99, 99.99, 5.00, 100, 'ACTIVE', TRUE);

-- Sample product images
INSERT INTO product_images (product_id, image_url, display_order, is_primary) VALUES
(1, 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=800&h=600&fit=crop', 0, TRUE),
(2, 'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=800&h=600&fit=crop', 0, TRUE),
(3, 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=800&h=600&fit=crop', 0, TRUE);

-- Sample coupons
INSERT INTO coupons (coupon_code, discount_type, discount_value, minimum_amount, maximum_discount, expiry_date, usage_limit, status) VALUES
('WELCOME10', 'PERCENTAGE', 10.00, 50.00, 50.00, DATE_ADD(CURDATE(), INTERVAL 30 DAY), 100, 'ACTIVE'),
('SAVE20', 'PERCENTAGE', 20.00, 100.00, 100.00, DATE_ADD(CURDATE(), INTERVAL 30 DAY), 50, 'ACTIVE'),
('FLAT50', 'FIXED', 50.00, 200.00, 50.00, DATE_ADD(CURDATE(), INTERVAL 30 DAY), 20, 'ACTIVE');

-- Sample hero banners
INSERT INTO hero_banners (title, subtitle, image_url, button_text, button_link, display_order, status) VALUES
('New Collection 2024', 'Elevate Your Lifestyle', 'https://images.unsplash.com/photo-1441986300917-64674bd600d8?w=1920&h=700&fit=crop', 'Shop Now', '/products', 0, 'ACTIVE'),
('Summer Sale!', 'Up to 50% Off', 'https://images.unsplash.com/photo-1483985988355-763728e1935b?w=1920&h=700&fit=crop', 'Grab Deals', '/sale', 1, 'ACTIVE');

-- ====================================================
-- DATABASE CREATION COMPLETE!
-- ====================================================
