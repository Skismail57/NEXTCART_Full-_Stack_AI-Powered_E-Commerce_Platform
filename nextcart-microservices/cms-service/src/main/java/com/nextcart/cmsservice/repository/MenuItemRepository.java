package com.nextcart.cmsservice.repository;

import com.nextcart.cmsservice.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    Optional<MenuItem> findById(Integer id);
    List<MenuItem> findByStatus(MenuItem.MenuItemStatus status);
    List<MenuItem> findByMenuType(String menuType);
}
