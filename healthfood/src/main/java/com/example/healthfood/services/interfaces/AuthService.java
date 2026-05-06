package com.example.healthfood.services.interfaces;

import com.example.healthfood.DTO.AuthRequest;
import com.example.healthfood.DTO.AuthResponse;

public interface AuthService {

    AuthResponse register(AuthRequest request);

    AuthResponse login(AuthRequest request);
}