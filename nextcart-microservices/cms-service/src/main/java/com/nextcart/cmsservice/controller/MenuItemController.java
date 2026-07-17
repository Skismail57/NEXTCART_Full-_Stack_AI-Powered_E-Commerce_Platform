package com.nextcart.cmsservice.controller;

import com.nextcart.cmsservice.dto.ApiResponse;
import com.nextcart.cmsservice.model.MenuItem;
import com.nextcart.cmsservice.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemController {
    private final MenuItemService menuItemService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MenuItem>>> getAllMenuItems() {
        return ResponseEntity.ok(ApiResponse.success(menuItemService.getAllMenuItems()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuItem>> getMenuItemById(@PathVariable Integer id) {
        return menuItemService.getMenuItemById(id)
                .map(item -> ResponseEntity.ok(ApiResponse.success(item)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<MenuItem>>> getActiveMenuItems() {
        return ResponseEntity.ok(ApiResponse.success(menuItemService.getActiveMenuItems()));
    }

    @GetMapping("/type/{menuType}")
    public ResponseEntity<ApiResponse<List<MenuItem>>> getMenuItemsByType(@PathVariable String menuType) {
        return ResponseEntity.ok(ApiResponse.success(menuItemService.getMenuItemsByMenuType(menuType)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MenuItem>> createMenuItem(@RequestBody MenuItem menuItem) {
        return new ResponseEntity<>(ApiResponse.success(menuItemService.createMenuItem(menuItem)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuItem>> updateMenuItem(@PathVariable Integer id, @RequestBody MenuItem menuItem) {
        return ResponseEntity.ok(ApiResponse.success(menuItemService.updateMenuItem(id, menuItem)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Integer id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }
}
