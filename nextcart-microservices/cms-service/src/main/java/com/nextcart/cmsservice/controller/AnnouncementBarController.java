package com.nextcart.cmsservice.controller;

import com.nextcart.cmsservice.dto.ApiResponse;
import com.nextcart.cmsservice.model.AnnouncementBar;
import com.nextcart.cmsservice.service.AnnouncementBarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcement-bars")
@RequiredArgsConstructor
public class AnnouncementBarController {
    private final AnnouncementBarService announcementBarService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AnnouncementBar>>> getAllAnnouncementBars() {
        return ResponseEntity.ok(ApiResponse.success(announcementBarService.getAllAnnouncementBars()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AnnouncementBar>> getAnnouncementBarById(@PathVariable Integer id) {
        return announcementBarService.getAnnouncementBarById(id)
                .map(bar -> ResponseEntity.ok(ApiResponse.success(bar)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<AnnouncementBar>>> getActiveAnnouncementBars() {
        return ResponseEntity.ok(ApiResponse.success(announcementBarService.getActiveAnnouncementBars()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AnnouncementBar>> createAnnouncementBar(@RequestBody AnnouncementBar announcementBar) {
        return new ResponseEntity<>(ApiResponse.success(announcementBarService.createAnnouncementBar(announcementBar)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AnnouncementBar>> updateAnnouncementBar(@PathVariable Integer id, @RequestBody AnnouncementBar announcementBar) {
        return ResponseEntity.ok(ApiResponse.success(announcementBarService.updateAnnouncementBar(id, announcementBar)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncementBar(@PathVariable Integer id) {
        announcementBarService.deleteAnnouncementBar(id);
        return ResponseEntity.noContent().build();
    }
}
