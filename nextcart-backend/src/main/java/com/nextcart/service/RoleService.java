package com.nextcart.service;

import com.nextcart.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<Role> getRoleById(Integer id);
    Optional<Role> getRoleByRoleName(String roleName);
    List<Role> getAllRoles();
    Role createRole(Role role);
    Role updateRole(Role role);
    boolean deleteRole(Integer id);
}
