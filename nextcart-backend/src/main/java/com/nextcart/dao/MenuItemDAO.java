package com.nextcart.dao;

import com.nextcart.model.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemDAO {
    Optional<MenuItem> findById(Integer id);
    List<MenuItem> findAll();
    List<MenuItem> findActive();
    List<MenuItem> findByMenuType(String menuType);
    List<MenuItem> findByParentId(Integer parentId);
    List<MenuItem> findRootMenuItems(String menuType);
    MenuItem create(MenuItem menuItem);
    MenuItem update(MenuItem menuItem);
    boolean delete(Integer id);
}
