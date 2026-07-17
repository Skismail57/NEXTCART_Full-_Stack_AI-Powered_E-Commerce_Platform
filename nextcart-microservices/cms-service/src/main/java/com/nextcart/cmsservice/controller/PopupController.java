package com.nextcart.cmsservice.controller;

import com.nextcart.cmsservice.dto.ApiResponse;
import com.nextcart.cmsservice.model.Popup;
import com.nextcart.cmsservice.service.PopupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/popups")
@RequiredArgsConstructor
public class PopupController {
    private final PopupService popupService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Popup>>> getAllPopups() {
        return ResponseEntity.ok(ApiResponse.success(popupService.getAllPopups()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Popup>> getPopupById(@PathVariable Integer id) {
        return popupService.getPopupById(id)
                .map(popup -> ResponseEntity.ok(ApiResponse.success(popup)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<Popup>>> getActivePopups() {
        return ResponseEntity.ok(ApiResponse.success(popupService.getActivePopups()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Popup>> createPopup(@RequestBody Popup popup) {
        return new ResponseEntity<>(ApiResponse.success(popupService.createPopup(popup)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Popup>> updatePopup(@PathVariable Integer id, @RequestBody Popup popup) {
        return ResponseEntity.ok(ApiResponse.success(popupService.updatePopup(id, popup)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePopup(@PathVariable Integer id) {
        popupService.deletePopup(id);
        return ResponseEntity.noContent().build();
    }
}
