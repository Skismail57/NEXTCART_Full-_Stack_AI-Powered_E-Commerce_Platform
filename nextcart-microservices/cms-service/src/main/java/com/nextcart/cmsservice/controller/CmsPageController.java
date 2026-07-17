package com.nextcart.cmsservice.controller;

import com.nextcart.cmsservice.dto.ApiResponse;
import com.nextcart.cmsservice.model.CmsPage;
import com.nextcart.cmsservice.service.CmsPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cms-pages")
@RequiredArgsConstructor
public class CmsPageController {
    private final CmsPageService cmsPageService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CmsPage>>> getAllCmsPages() {
        return ResponseEntity.ok(ApiResponse.success(cmsPageService.getAllCmsPages()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CmsPage>> getCmsPageById(@PathVariable Integer id) {
        return cmsPageService.getCmsPageById(id)
                .map(page -> ResponseEntity.ok(ApiResponse.success(page)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<CmsPage>> getCmsPageBySlug(@PathVariable String slug) {
        return cmsPageService.getCmsPageBySlug(slug)
                .map(page -> ResponseEntity.ok(ApiResponse.success(page)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<CmsPage>>> getActiveCmsPages() {
        return ResponseEntity.ok(ApiResponse.success(cmsPageService.getActiveCmsPages()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CmsPage>> createCmsPage(@RequestBody CmsPage cmsPage) {
        return new ResponseEntity<>(ApiResponse.success(cmsPageService.createCmsPage(cmsPage)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CmsPage>> updateCmsPage(@PathVariable Integer id, @RequestBody CmsPage cmsPage) {
        return ResponseEntity.ok(ApiResponse.success(cmsPageService.updateCmsPage(id, cmsPage)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCmsPage(@PathVariable Integer id) {
        cmsPageService.deleteCmsPage(id);
        return ResponseEntity.noContent().build();
    }
}
