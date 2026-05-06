package com.example.healthfood.controllers;

import com.example.healthfood.DAO.entities.core.HealthProfile;
import com.example.healthfood.services.interfaces.HealthProfileService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class HealthProfileController {

    private final HealthProfileService healthProfileService;

    // ✅ CREATE / UPDATE
    @PostMapping
    public HealthProfile saveProfile(@RequestBody HealthProfile profile,
                                     Authentication authentication) {

        String email = authentication.getName();

        return healthProfileService.createOrUpdateProfile(profile, email);
    }

    // ✅ GET PROFILE
    @GetMapping
    public HealthProfile getProfile(Authentication authentication) {

        String email = authentication.getName();

        return healthProfileService.getProfile(email);
    }
}