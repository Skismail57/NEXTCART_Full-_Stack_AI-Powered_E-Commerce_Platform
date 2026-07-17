package com.nextcart.service;

import com.nextcart.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Integer id);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    User registerUser(String firstName, String lastName, String email, String password);
    Map<String, Object> login(String email, String password);
    User updateUser(User user);
    void deleteUser(Integer id);
}
