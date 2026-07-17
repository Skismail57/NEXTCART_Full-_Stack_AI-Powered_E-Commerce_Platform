package com.nextcart.serviceImpl;

import com.nextcart.dao.MenuItemDAO;
import com.nextcart.daoImpl.MenuItemDAOImpl;
import com.nextcart.model.MenuItem;
import com.nextcart.service.MenuItemService;

import java.util.List;
import java.util.Optional;

public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemDAO menuItemDAO = new MenuItemDAOImpl();

    @Override
    public Optional<MenuItem> getMenuItemById(Integer id) {
        return menuItemDAO.findById(id);
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemDAO.findAll();
    }

    @Override
    public List<MenuItem> getActiveMenuItems() {
        return menuItemDAO.findActive();
    }

    @Override
    public List<MenuItem> getMenuItemsByMenuType(String menuType) {
        return menuItemDAO.findByMenuType(menuType);
    }

    @Override
    public List<MenuItem> getMenuItemsByParentId(Integer parentId) {
        return menuItemDAO.findByParentId(parentId);
    }

    @Override
    public List<MenuItem> getRootMenuItems(String menuType) {
        return menuItemDAO.findRootMenuItems(menuType);
    }

    @Override
    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemDAO.create(menuItem);
    }

    @Override
    public MenuItem updateMenuItem(MenuItem menuItem) {
        return menuItemDAO.update(menuItem);
    }

    @Override
    public boolean deleteMenuItem(Integer id) {
        return menuItemDAO.delete(id);
    }
}
