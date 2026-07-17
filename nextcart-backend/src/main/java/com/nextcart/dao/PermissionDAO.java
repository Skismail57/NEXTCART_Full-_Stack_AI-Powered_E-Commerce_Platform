package com.nextcart.dao;

import com.nextcart.model.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionDAO {
    Optional<Permission> findById(Integer id);
    Optional<Permission> findBySlug(String slug);
    List<Permission> findAll();
    Permission create(Permission permission);
    Permission update(Permission permission);
    boolean delete(Integer id);
}
