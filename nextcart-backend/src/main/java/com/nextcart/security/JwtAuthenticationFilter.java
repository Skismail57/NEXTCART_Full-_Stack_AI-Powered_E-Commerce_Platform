package com.nextcart.security;

import com.nextcart.constants.AppConstants;
import com.nextcart.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter(urlPatterns = "/api/*")
public class JwtAuthenticationFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestPath = httpRequest.getRequestURI();
        logger.info("Request path: {}", requestPath);

        if (isPublicEndpoint(requestPath)) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = httpRequest.getHeader(AppConstants.AUTH_HEADER);
        if (authHeader == null || !authHeader.startsWith(AppConstants.BEARER_PREFIX)) {
            logger.warn("Missing or invalid Authorization header");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"success\":false,\"message\":\"Missing or invalid Authorization header\"}");
            return;
        }

        String token = authHeader.substring(AppConstants.BEARER_PREFIX.length());
        if (!JwtUtil.validateToken(token)) {
            logger.warn("Invalid JWT token");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"success\":false,\"message\":\"Invalid or expired token\"}");
            return;
        }

        Integer userId = JwtUtil.getUserIdFromToken(token);
        String email = JwtUtil.getEmailFromToken(token);
        String role = JwtUtil.getRoleFromToken(token);

        httpRequest.setAttribute("userId", userId);
        httpRequest.setAttribute("email", email);
        httpRequest.setAttribute("role", role);

        logger.debug("Authenticated user: {}, Role: {}", email, role);
        chain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String requestPath) {
        for (String endpoint : AppConstants.PUBLIC_ENDPOINTS) {
            if (requestPath.contains(endpoint)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
