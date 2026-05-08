package com.example.healthfood.controllers;

import com.example.healthfood.DAO.entities.core.HealthProfile;
import com.example.healthfood.DTO.HealthProfileDTO;
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
    public HealthProfileDTO saveProfile(@RequestBody HealthProfile profile,
                                        Authentication authentication) {

        String email = authentication.getName();

        HealthProfile saved = healthProfileService.createOrUpdateProfile(profile, email);

        return healthProfileService.toDTO(saved);
    }

    // ✅ GET PROFILE
    @GetMapping
    public HealthProfileDTO getProfile(Authentication authentication) {

        String email = authentication.getName();

        HealthProfile profile = healthProfileService.getProfile(email);

        return healthProfileService.toDTO(profile);
    }
}