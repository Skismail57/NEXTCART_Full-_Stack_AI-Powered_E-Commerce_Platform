package com.nextcart.cmsservice.service;

import com.nextcart.cmsservice.model.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemService {
    List<MenuItem> getAllMenuItems();
    Optional<MenuItem> getMenuItemById(Integer id);
    List<MenuItem> getActiveMenuItems();
    List<MenuItem> getMenuItemsByMenuType(String menuType);
    MenuItem createMenuItem(MenuItem menuItem);
    MenuItem updateMenuItem(Integer id, MenuItem menuItem);
    void deleteMenuItem(Integer id);
}
