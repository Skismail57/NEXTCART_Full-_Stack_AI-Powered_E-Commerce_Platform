package com.nextcart.apigateway.filter;

import com.nextcart.apigateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JwtUtil jwtUtil;

    public AuthenticationFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Skip authentication for specific paths
            String path = exchange.getRequest().getURI().getPath();
            if (path.startsWith("/api/v1/auth/") || path.startsWith("/actuator/")) {
                return chain.filter(exchange);
            }

            // Check for Authorization header
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);
            if (!jwtUtil.validateToken(token)) {
                exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // Add user info to request headers for downstream services
            String username = jwtUtil.extractUsername(token);
            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(request -> request.header("X-User-Name", username))
                    .build();

            return chain.filter(modifiedExchange);
        };
    }

    public static class Config {
        // Configuration properties if needed
    }
}