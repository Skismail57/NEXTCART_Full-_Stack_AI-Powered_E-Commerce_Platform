package com.nextcart.cmsservice.service.impl;

import com.nextcart.cmsservice.model.MenuItem;
import com.nextcart.cmsservice.repository.MenuItemRepository;
import com.nextcart.cmsservice.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public Optional<MenuItem> getMenuItemById(Integer id) {
        return menuItemRepository.findById(id);
    }

    @Override
    public List<MenuItem> getActiveMenuItems() {
        return menuItemRepository.findByStatus(MenuItem.MenuItemStatus.ACTIVE);
    }

    @Override
    public List<MenuItem> getMenuItemsByMenuType(String menuType) {
        return menuItemRepository.findByMenuType(menuType);
    }

    @Override
    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItem(Integer id, MenuItem menuItem) {
        return menuItemRepository.findById(id)
                .map(existing -> {
                    existing.setParentId(menuItem.getParentId());
                    existing.setTitle(menuItem.getTitle());
                    existing.setSlug(menuItem.getSlug());
                    existing.setUrl(menuItem.getUrl());
                    existing.setIcon(menuItem.getIcon());
                    existing.setImage(menuItem.getImage());
                    existing.setDisplayOrder(menuItem.getDisplayOrder());
                    existing.setStatus(menuItem.getStatus());
                    existing.setMenuType(menuItem.getMenuType());
                    return menuItemRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
    }

    @Override
    public void deleteMenuItem(Integer id) {
        menuItemRepository.deleteById(id);
    }
}
