package com.nextcart.constants;

public class AppConstants {
    private AppConstants() {
        // Private constructor to prevent instantiation
    }

    public static final String API_VERSION = "v1";
    public static final String BASE_API_PATH = "/api/" + API_VERSION;

    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;

    public static final String DEFAULT_UPLOAD_DIR = "uploads";
    public static final long DEFAULT_MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB

    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    public static final String[] PUBLIC_ENDPOINTS = {
            "/auth/login",
            "/auth/register",
            "/products",
            "/categories",
            "/sub-categories",
            "/subcategories",
            "/brands",
            "/banners",
            "/hero-banners",
            "/coupons",
            "/test"
    };
}
