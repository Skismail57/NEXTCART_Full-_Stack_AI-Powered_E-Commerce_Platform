package com.nextcart.cmsservice.controller;

import com.nextcart.cmsservice.dto.ApiResponse;
import com.nextcart.cmsservice.model.Banner;
import com.nextcart.cmsservice.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banners")
@RequiredArgsConstructor
public class BannerController {
    private final BannerService bannerService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Banner>>> getAllBanners() {
        return ResponseEntity.ok(ApiResponse.success(bannerService.getAllBanners()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Banner>> getBannerById(@PathVariable Integer id) {
        return bannerService.getBannerById(id)
                .map(banner -> ResponseEntity.ok(ApiResponse.success(banner)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<Banner>>> getActiveBanners() {
        return ResponseEntity.ok(ApiResponse.success(bannerService.getActiveBanners()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Banner>> createBanner(@RequestBody Banner banner) {
        return new ResponseEntity<>(ApiResponse.success(bannerService.createBanner(banner)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Banner>> updateBanner(@PathVariable Integer id, @RequestBody Banner banner) {
        return ResponseEntity.ok(ApiResponse.success(bannerService.updateBanner(id, banner)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Integer id) {
        bannerService.deleteBanner(id);
        return ResponseEntity.noContent().build();
    }
}
