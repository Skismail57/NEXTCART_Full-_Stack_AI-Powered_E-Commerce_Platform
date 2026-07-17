package com.nextcart.serviceImpl;

import com.nextcart.dao.UserDAO;
import com.nextcart.daoImpl.UserDAOImpl;
import com.nextcart.model.User;
import com.nextcart.service.UserService;
import com.nextcart.util.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public Optional<User> getUserById(Integer id) {
        return userDAO.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) {
        if (userDAO.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordHash);
        user.setRoleId(1);
        user.setStatus("ACTIVE");
        user.setEmailVerified(false);
        user.setMobileVerified(false);
        return userDAO.create(user);
    }

    @Override
    public Map<String, Object> login(String email, String password) {
        Optional<User> userOpt = userDAO.authenticate(email, password);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        User user = userOpt.get();

        if (!"ACTIVE".equals(user.getStatus())) {
            throw new RuntimeException("Account is not active");
        }

        String token = JwtUtil.generateToken(user.getUserId(), user.getEmail(), user.getRoleName());

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", sanitizeUser(user));

        return result;
    }

    @Override
    public User updateUser(User user) {
        return userDAO.update(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userDAO.delete(id);
    }

    private Map<String, Object> sanitizeUser(User user) {
        Map<String, Object> sanitized = new HashMap<>();
        sanitized.put("id", user.getUserId());
        sanitized.put("firstName", user.getFirstName());
        sanitized.put("lastName", user.getLastName());
        sanitized.put("email", user.getEmail());
        sanitized.put("mobile", user.getMobile());
        sanitized.put("profileImage", user.getProfileImage());
        return sanitized;
    }
}
