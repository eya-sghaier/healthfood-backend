package com.example.healthfood.services.implementations;

import com.example.healthfood.DAO.entities.core.User;
import com.example.healthfood.DAO.repositories.UserRepository;
import com.example.healthfood.DTO.AuthRequest;
import com.example.healthfood.DTO.AuthResponse;
import com.example.healthfood.security.jwt.JwtService;
import com.example.healthfood.services.interfaces.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(AuthRequest request) {

        User user = new User();
        user.setEmail(request.getEmail());

        // 🔐 password encryption
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔐 verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
}