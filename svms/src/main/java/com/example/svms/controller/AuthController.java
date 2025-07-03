package com.example.svms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.svms.config.JwtTokenProvider;
import com.example.svms.dto.JwtResponseDTO;
import com.example.svms.dto.LoginRequestDTO;
import com.example.svms.model.User;
import com.example.svms.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Allowed roles
    private static final Set<String> VALID_ROLES = Set.of("ADMIN", "RECEPTIONIST", "VISITOR", "GUARD", "HOST");

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        if (user.getRole() == null || !VALID_ROLES.contains(user.getRole().toUpperCase())) {
            return ResponseEntity.badRequest().body("Invalid role. Allowed roles: " + VALID_ROLES);
        }

        user.setRole(user.getRole().toUpperCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully as " + user.getRole());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        System.out.println("🔐 Login attempt for: " + request.getUsername());

        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
                )
            );

            Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(401).body("User not found");
            }

            User user = userOpt.get();

            String token = jwtTokenProvider.generateToken(user.getUsername(), user.getRole());
            return ResponseEntity.ok(new JwtResponseDTO(token, user.getUsername(), user.getRole()));

        } catch (Exception e) {
            System.out.println("❌ Login failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}