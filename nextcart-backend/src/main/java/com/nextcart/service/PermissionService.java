package com.nextcart.service;

import com.nextcart.model.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionService {
    Optional<Permission> getPermissionById(Integer id);
    Optional<Permission> getPermissionBySlug(String slug);
    List<Permission> getAllPermissions();
    Permission createPermission(Permission permission);
    Permission updatePermission(Permission permission);
    boolean deletePermission(Integer id);
}
