package com.nextcart.cmsservice.controller;

import com.nextcart.cmsservice.dto.ApiResponse;
import com.nextcart.cmsservice.model.HeroBanner;
import com.nextcart.cmsservice.service.HeroBannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hero-banners")
@RequiredArgsConstructor
public class HeroBannerController {
    private final HeroBannerService heroBannerService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<HeroBanner>>> getAllHeroBanners() {
        return ResponseEntity.ok(ApiResponse.success(heroBannerService.getAllHeroBanners()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HeroBanner>> getHeroBannerById(@PathVariable Integer id) {
        return heroBannerService.getHeroBannerById(id)
                .map(banner -> ResponseEntity.ok(ApiResponse.success(banner)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<HeroBanner>>> getActiveHeroBanners() {
        return ResponseEntity.ok(ApiResponse.success(heroBannerService.getActiveHeroBanners()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<HeroBanner>> createHeroBanner(@RequestBody HeroBanner heroBanner) {
        return new ResponseEntity<>(ApiResponse.success(heroBannerService.createHeroBanner(heroBanner)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HeroBanner>> updateHeroBanner(@PathVariable Integer id, @RequestBody HeroBanner heroBanner) {
        return ResponseEntity.ok(ApiResponse.success(heroBannerService.updateHeroBanner(id, heroBanner)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHeroBanner(@PathVariable Integer id) {
        heroBannerService.deleteHeroBanner(id);
        return ResponseEntity.noContent().build();
    }
}
