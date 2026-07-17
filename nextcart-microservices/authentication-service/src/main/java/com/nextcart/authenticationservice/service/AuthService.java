package com.nextcart.authenticationservice.service;

import com.nextcart.authenticationservice.dto.LoginRequest;
import com.nextcart.authenticationservice.dto.LoginResponse;
import com.nextcart.authenticationservice.dto.RegisterRequest;
import com.nextcart.authenticationservice.model.Role;
import com.nextcart.authenticationservice.model.User;
import com.nextcart.authenticationservice.repository.RoleRepository;
import com.nextcart.authenticationservice.repository.UserRepository;
import com.nextcart.authenticationservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        if (user.getStatus() != User.UserStatus.ACTIVE) {
            throw new RuntimeException("User account is not active");
        }

        String token = jwtUtil.generateToken(user.getUserId(), user.getEmail(), user.getRole().getRoleName());

        return new LoginResponse(
                token,
                "Bearer",
                user.getUserId(),
                user.getEmail(),
                user.getRole().getRoleName(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        Role customerRole = roleRepository.findByRoleName("CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(customerRole);
        user.setStatus(User.UserStatus.ACTIVE);
        user.setEmailVerified(false);
        user.setMobileVerified(false);

        userRepository.save(user);
    }

    public void resetPassword(String email, String newPassword, String otp) {
        // TODO: Implement OTP verification logic
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
