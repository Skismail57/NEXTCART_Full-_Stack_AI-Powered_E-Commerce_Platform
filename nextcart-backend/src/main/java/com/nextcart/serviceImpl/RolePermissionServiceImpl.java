package com.nextcart.serviceImpl;

import com.nextcart.dao.RolePermissionDAO;
import com.nextcart.daoImpl.RolePermissionDAOImpl;
import com.nextcart.model.RolePermission;
import com.nextcart.service.RolePermissionService;

import java.util.List;
import java.util.Optional;

public class RolePermissionServiceImpl implements RolePermissionService {
    private final RolePermissionDAO rolePermissionDAO = new RolePermissionDAOImpl();

    @Override
    public Optional<RolePermission> getRolePermissionById(Integer id) {
        return rolePermissionDAO.findById(id);
    }

    @Override
    public List<RolePermission> getRolePermissionsByRoleId(Integer roleId) {
        return rolePermissionDAO.findByRoleId(roleId);
    }

    @Override
    public List<RolePermission> getRolePermissionsByPermissionId(Integer permissionId) {
        return rolePermissionDAO.findByPermissionId(permissionId);
    }

    @Override
    public List<RolePermission> getAllRolePermissions() {
        return rolePermissionDAO.findAll();
    }

    @Override
    public RolePermission createRolePermission(RolePermission rolePermission) {
        return rolePermissionDAO.create(rolePermission);
    }

    @Override
    public boolean deleteRolePermissionByRoleIdAndPermissionId(Integer roleId, Integer permissionId) {
        return rolePermissionDAO.deleteByRoleIdAndPermissionId(roleId, permissionId);
    }

    @Override
    public boolean deleteRolePermission(Integer id) {
        return rolePermissionDAO.delete(id);
    }
}
