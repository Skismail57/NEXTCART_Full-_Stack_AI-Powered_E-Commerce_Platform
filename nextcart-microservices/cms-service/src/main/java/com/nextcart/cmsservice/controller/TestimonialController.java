package com.nextcart.cmsservice.controller;

import com.nextcart.cmsservice.dto.ApiResponse;
import com.nextcart.cmsservice.model.Testimonial;
import com.nextcart.cmsservice.service.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testimonials")
@RequiredArgsConstructor
public class TestimonialController {
    private final TestimonialService testimonialService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Testimonial>>> getAllTestimonials() {
        return ResponseEntity.ok(ApiResponse.success(testimonialService.getAllTestimonials()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Testimonial>> getTestimonialById(@PathVariable Integer id) {
        return testimonialService.getTestimonialById(id)
                .map(testimonial -> ResponseEntity.ok(ApiResponse.success(testimonial)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<Testimonial>>> getActiveTestimonials() {
        return ResponseEntity.ok(ApiResponse.success(testimonialService.getActiveTestimonials()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Testimonial>> createTestimonial(@RequestBody Testimonial testimonial) {
        return new ResponseEntity<>(ApiResponse.success(testimonialService.createTestimonial(testimonial)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Testimonial>> updateTestimonial(@PathVariable Integer id, @RequestBody Testimonial testimonial) {
        return ResponseEntity.ok(ApiResponse.success(testimonialService.updateTestimonial(id, testimonial)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestimonial(@PathVariable Integer id) {
        testimonialService.deleteTestimonial(id);
        return ResponseEntity.noContent().build();
    }
}
