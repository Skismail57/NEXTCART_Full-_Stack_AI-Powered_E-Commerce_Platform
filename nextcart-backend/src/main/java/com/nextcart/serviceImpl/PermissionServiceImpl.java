package com.nextcart.serviceImpl;

import com.nextcart.dao.PermissionDAO;
import com.nextcart.daoImpl.PermissionDAOImpl;
import com.nextcart.model.Permission;
import com.nextcart.service.PermissionService;

import java.util.List;
import java.util.Optional;

public class PermissionServiceImpl implements PermissionService {
    private final PermissionDAO permissionDAO = new PermissionDAOImpl();

    @Override
    public Optional<Permission> getPermissionById(Integer id) {
        return permissionDAO.findById(id);
    }

    @Override
    public Optional<Permission> getPermissionBySlug(String slug) {
        return permissionDAO.findBySlug(slug);
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionDAO.findAll();
    }

    @Override
    public Permission createPermission(Permission permission) {
        return permissionDAO.create(permission);
    }

    @Override
    public Permission updatePermission(Permission permission) {
        return permissionDAO.update(permission);
    }

    @Override
    public boolean deletePermission(Integer id) {
        return permissionDAO.delete(id);
    }
}
