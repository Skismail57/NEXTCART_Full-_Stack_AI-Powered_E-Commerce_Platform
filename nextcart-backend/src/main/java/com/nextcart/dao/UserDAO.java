package com.nextcart.dao;

import com.nextcart.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDAO {
    Optional<User> findById(Integer userId);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    User create(User user);
    User update(User user);
    boolean delete(Integer userId);
    Optional<User> authenticate(String email, String password);
}
