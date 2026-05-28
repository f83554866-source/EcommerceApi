package com.ws101.fformaran.controller;

import com.ws101.fformaran.dto.RegisterUserDto;
import com.ws101.fformaran.model.User;
import com.ws101.fformaran.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // --- NEW ENDPOINT: Forces Spring to generate the token ---
    @GetMapping("/csrf")
    public CsrfToken getCsrfToken(CsrfToken token) {
        return token;
    }

    // --- NEW ENDPOINT: Task 7.3 Fetch User Info ---
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()") // Forces a 401 error if not logged in
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        return ResponseEntity.ok(Map.of("username", authentication.getName()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserDto dto) {
        if (userRepository.findByUsername(dto.username()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(dto.role());

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}