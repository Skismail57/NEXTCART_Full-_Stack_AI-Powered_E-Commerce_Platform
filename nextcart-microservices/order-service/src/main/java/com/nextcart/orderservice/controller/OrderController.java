package com.nextcart.orderservice.controller;

import com.nextcart.orderservice.dto.ApiResponse;
import com.nextcart.orderservice.model.Order;
import com.nextcart.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {
        return ResponseEntity.ok(ApiResponse.success(orderRepository.findAll()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Order>>> getOrdersByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(ApiResponse.success(orderRepository.findByUserId(userId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable Integer id) {
        return orderRepository.findById(id)
                .map(order -> ResponseEntity.ok(ApiResponse.success(order)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> createOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(orderRepository.save(order)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> updateOrder(@PathVariable Integer id, @RequestBody Order order) {
        return orderRepository.findById(id).map(existing -> {
            existing.setOrderStatus(order.getOrderStatus());
            existing.setPaymentStatus(order.getPaymentStatus());
            return ResponseEntity.ok(ApiResponse.success(orderRepository.save(existing)));
        }).orElse(ResponseEntity.notFound().build());
    }
}
