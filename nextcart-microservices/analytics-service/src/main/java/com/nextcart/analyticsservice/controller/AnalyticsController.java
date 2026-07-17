package com.nextcart.analyticsservice.controller;

import com.nextcart.analyticsservice.dto.ApiResponse;
import com.nextcart.analyticsservice.model.DailyAnalytics;
import com.nextcart.analyticsservice.service.DailyAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final DailyAnalyticsService analyticsService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DailyAnalytics>>> getAllAnalytics() {
        return ResponseEntity.ok(ApiResponse.success(analyticsService.getAllAnalytics()));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<ApiResponse<DailyAnalytics>> getByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return analyticsService.getAnalyticsByDate(date)
                .map(analytics -> ResponseEntity.ok(ApiResponse.success(analytics)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/range")
    public ResponseEntity<ApiResponse<List<DailyAnalytics>>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(ApiResponse.success(analyticsService.getAnalyticsByDateRange(startDate, endDate)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DailyAnalytics>> createAnalytics(@RequestBody DailyAnalytics analytics) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(analyticsService.createAnalytics(analytics)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DailyAnalytics>> updateAnalytics(
            @PathVariable Integer id, @RequestBody DailyAnalytics analytics) {
        return ResponseEntity.ok(ApiResponse.success(analyticsService.updateAnalytics(id, analytics)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnalytics(@PathVariable Integer id) {
        analyticsService.deleteAnalytics(id);
        return ResponseEntity.noContent().build();
    }
}
