package com.nextcart.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;

//@WebFilter(urlPatterns = "/api/admin/*")
public class AuthorizationFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);
    private static final Set<String> ADMIN_ENDPOINTS = Set.of(
            "/api/admin"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestPath = httpRequest.getRequestURI();
        String role = (String) httpRequest.getAttribute("role");

        boolean isAdminEndpoint = ADMIN_ENDPOINTS.stream().anyMatch(requestPath::startsWith);

        if (isAdminEndpoint && !"ADMIN".equals(role)) {
            logger.warn("Unauthorized access attempt to admin endpoint by role: {}", role);
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"success\":false,\"message\":\"Access denied. Admin privileges required.\"}");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
