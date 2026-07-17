package com.nextcart.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

//@WebFilter(urlPatterns = "/api/*")
public class RequestValidationFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequestValidationFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String method = httpRequest.getMethod();
        String contentType = httpRequest.getContentType();

        if ((method.equals("POST") || method.equals("PUT"))
                && (contentType == null || !contentType.contains("application/json"))) {
            logger.warn("Invalid content type for {} request: {}", method, contentType);
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"success\":false,\"message\":\"Content-Type must be application/json\"}");
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
