package com.nextcart.dao;

import com.nextcart.model.RolePermission;

import java.util.List;
import java.util.Optional;

public interface RolePermissionDAO {
    Optional<RolePermission> findById(Integer id);
    List<RolePermission> findByRoleId(Integer roleId);
    List<RolePermission> findByPermissionId(Integer permissionId);
    List<RolePermission> findAll();
    RolePermission create(RolePermission rolePermission);
    boolean deleteByRoleIdAndPermissionId(Integer roleId, Integer permissionId);
    boolean delete(Integer id);
}
