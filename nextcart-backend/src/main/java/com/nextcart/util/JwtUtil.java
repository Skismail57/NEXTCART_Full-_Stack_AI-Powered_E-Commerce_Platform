package com.nextcart.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private static final String SECRET_KEY;
    private static final long EXPIRATION;

    static {
        try {
            Properties props = new Properties();
            InputStream inputStream = JwtUtil.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");

            if (inputStream == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }

            props.load(inputStream);
            SECRET_KEY = props.getProperty("jwt.secret");
            EXPIRATION = Long.parseLong(props.getProperty("jwt.expiration"));

        } catch (IOException e) {
            logger.error("Failed to load JWT configuration", e);
            throw new RuntimeException("Failed to load JWT configuration", e);
        }
    }

    private static SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public static String generateToken(Integer userId, String email, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION);

        return Jwts.builder()
                .subject(userId.toString())
                .claim("email", email)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public static Integer getUserIdFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
        Claims claims = claimsJws.getPayload();

        return Integer.parseInt(claims.getSubject());
    }

    public static String getEmailFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
        Claims claims = claimsJws.getPayload();

        return claims.get("email", String.class);
    }

    public static String getRoleFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
        Claims claims = claimsJws.getPayload();

        return claims.get("role", String.class);
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Invalid JWT token", e);
            return false;
        }
    }
}
