package com.nextcart.service;

import com.nextcart.model.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemService {
    Optional<MenuItem> getMenuItemById(Integer id);
    List<MenuItem> getAllMenuItems();
    List<MenuItem> getActiveMenuItems();
    List<MenuItem> getMenuItemsByMenuType(String menuType);
    List<MenuItem> getMenuItemsByParentId(Integer parentId);
    List<MenuItem> getRootMenuItems(String menuType);
    MenuItem createMenuItem(MenuItem menuItem);
    MenuItem updateMenuItem(MenuItem menuItem);
    boolean deleteMenuItem(Integer id);
}
