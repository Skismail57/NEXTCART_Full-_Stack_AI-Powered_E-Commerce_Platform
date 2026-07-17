package com.nextcart.cmsservice.controller;

import com.nextcart.cmsservice.dto.ApiResponse;
import com.nextcart.cmsservice.model.HomepageSection;
import com.nextcart.cmsservice.service.HomepageSectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/homepage-sections")
@RequiredArgsConstructor
public class HomepageSectionController {
    private final HomepageSectionService homepageSectionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<HomepageSection>>> getAllHomepageSections() {
        return ResponseEntity.ok(ApiResponse.success(homepageSectionService.getAllHomepageSections()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HomepageSection>> getHomepageSectionById(@PathVariable Integer id) {
        return homepageSectionService.getHomepageSectionById(id)
                .map(section -> ResponseEntity.ok(ApiResponse.success(section)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<HomepageSection>>> getActiveHomepageSections() {
        return ResponseEntity.ok(ApiResponse.success(homepageSectionService.getActiveHomepageSections()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<HomepageSection>> createHomepageSection(@RequestBody HomepageSection homepageSection) {
        return new ResponseEntity<>(ApiResponse.success(homepageSectionService.createHomepageSection(homepageSection)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HomepageSection>> updateHomepageSection(@PathVariable Integer id, @RequestBody HomepageSection homepageSection) {
        return ResponseEntity.ok(ApiResponse.success(homepageSectionService.updateHomepageSection(id, homepageSection)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHomepageSection(@PathVariable Integer id) {
        homepageSectionService.deleteHomepageSection(id);
        return ResponseEntity.noContent().build();
    }
}
