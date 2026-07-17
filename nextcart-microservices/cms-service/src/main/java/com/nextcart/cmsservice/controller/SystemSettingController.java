package com.nextcart.cmsservice.controller;

import com.nextcart.cmsservice.dto.ApiResponse;
import com.nextcart.cmsservice.model.SystemSetting;
import com.nextcart.cmsservice.service.SystemSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system-settings")
@RequiredArgsConstructor
public class SystemSettingController {
    private final SystemSettingService systemSettingService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SystemSetting>>> getAllSystemSettings() {
        return ResponseEntity.ok(ApiResponse.success(systemSettingService.getAllSystemSettings()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SystemSetting>> getSystemSettingById(@PathVariable Integer id) {
        return systemSettingService.getSystemSettingById(id)
                .map(setting -> ResponseEntity.ok(ApiResponse.success(setting)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/key/{key}")
    public ResponseEntity<ApiResponse<SystemSetting>> getSystemSettingByKey(@PathVariable String key) {
        return systemSettingService.getSystemSettingByKey(key)
                .map(setting -> ResponseEntity.ok(ApiResponse.success(setting)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SystemSetting>> createSystemSetting(@RequestBody SystemSetting systemSetting) {
        return new ResponseEntity<>(ApiResponse.success(systemSettingService.createSystemSetting(systemSetting)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SystemSetting>> updateSystemSetting(@PathVariable Integer id, @RequestBody SystemSetting systemSetting) {
        return ResponseEntity.ok(ApiResponse.success(systemSettingService.updateSystemSetting(id, systemSetting)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSystemSetting(@PathVariable Integer id) {
        systemSettingService.deleteSystemSetting(id);
        return ResponseEntity.noContent().build();
    }
}
