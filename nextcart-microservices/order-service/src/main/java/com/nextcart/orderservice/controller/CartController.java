package com.nextcart.orderservice.controller;

import com.nextcart.orderservice.dto.ApiResponse;
import com.nextcart.orderservice.model.Cart;
import com.nextcart.orderservice.model.CartItem;
import com.nextcart.orderservice.repository.CartItemRepository;
import com.nextcart.orderservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Optional<Cart>>> getCartByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(ApiResponse.success(cartRepository.findByUserId(userId)));
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<ApiResponse<List<CartItem>>> getCartItems(@PathVariable Integer cartId) {
        return ResponseEntity.ok(ApiResponse.success(cartItemRepository.findByCartId(cartId)));
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<ApiResponse<CartItem>> addItemToCart(@PathVariable Integer cartId, @RequestBody CartItem cartItem) {
        cartItem.setCartId(cartId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(cartItemRepository.save(cartItem)));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Integer itemId) {
        cartItemRepository.deleteById(itemId);
        return ResponseEntity.noContent().build();
    }
}
