package com.nextcart.serviceImpl;

import com.nextcart.dao.RoleDAO;
import com.nextcart.daoImpl.RoleDAOImpl;
import com.nextcart.model.Role;
import com.nextcart.service.RoleService;

import java.util.List;
import java.util.Optional;

public class RoleServiceImpl implements RoleService {
    private final RoleDAO roleDAO = new RoleDAOImpl();

    @Override
    public Optional<Role> getRoleById(Integer id) {
        return roleDAO.findById(id);
    }

    @Override
    public Optional<Role> getRoleByRoleName(String roleName) {
        return roleDAO.findByRoleName(roleName);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDAO.findAll();
    }

    @Override
    public Role createRole(Role role) {
        return roleDAO.create(role);
    }

    @Override
    public Role updateRole(Role role) {
        return roleDAO.update(role);
    }

    @Override
    public boolean deleteRole(Integer id) {
        return roleDAO.delete(id);
    }
}
