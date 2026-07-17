package com.nextcart.dao;

import com.nextcart.model.Role;
import java.util.List;
import java.util.Optional;

public interface RoleDAO {
    Optional<Role> findById(Integer roleId);
    Optional<Role> findByRoleName(String roleName);
    List<Role> findAll();
    Role create(Role role);
    Role update(Role role);
    boolean delete(Integer roleId);
}
