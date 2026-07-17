package com.nextcart.orderservice.controller;

import com.nextcart.orderservice.dto.ApiResponse;
import com.nextcart.orderservice.model.Wishlist;
import com.nextcart.orderservice.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Wishlist>>> getWishlistByUserId(@PathVariable Integer userId) {
        List<Wishlist> wishlist = wishlistService.getWishlistByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(wishlist));
    }

    @PostMapping("/user/{userId}/product/{productId}")
    public ResponseEntity<ApiResponse<Wishlist>> addToWishlist(@PathVariable Integer userId, @PathVariable Integer productId) {
        return wishlistService.addToWishlist(userId, productId)
                .map(w -> ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Product added to wishlist", w)))
                .orElse(ResponseEntity.badRequest().body(ApiResponse.error("Failed to add product to wishlist")));
    }

    @DeleteMapping("/user/{userId}/product/{productId}")
    public ResponseEntity<ApiResponse<Void>> removeFromWishlist(@PathVariable Integer userId, @PathVariable Integer productId) {
        wishlistService.removeFromWishlist(userId, productId);
        return ResponseEntity.ok(ApiResponse.success("Product removed from wishlist", null));
    }
}
