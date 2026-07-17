package com.nextcart.service;

import com.nextcart.model.RolePermission;

import java.util.List;
import java.util.Optional;

public interface RolePermissionService {
    Optional<RolePermission> getRolePermissionById(Integer id);
    List<RolePermission> getRolePermissionsByRoleId(Integer roleId);
    List<RolePermission> getRolePermissionsByPermissionId(Integer permissionId);
    List<RolePermission> getAllRolePermissions();
    RolePermission createRolePermission(RolePermission rolePermission);
    boolean deleteRolePermissionByRoleIdAndPermissionId(Integer roleId, Integer permissionId);
    boolean deleteRolePermission(Integer id);
}
