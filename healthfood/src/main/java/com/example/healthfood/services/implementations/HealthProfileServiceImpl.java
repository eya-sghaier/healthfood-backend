package com.example.healthfood.services.implementations;

import com.example.healthfood.DAO.entities.core.HealthProfile;
import com.example.healthfood.DAO.entities.core.User;
import com.example.healthfood.DAO.repositories.HealthProfileRepository;
import com.example.healthfood.DAO.repositories.UserRepository;
import com.example.healthfood.DTO.HealthProfileDTO;
import com.example.healthfood.services.interfaces.HealthProfileService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class HealthProfileServiceImpl implements HealthProfileService {

    private final HealthProfileRepository healthProfileRepository;
    private final UserRepository userRepository;

    @Override
    public HealthProfile createOrUpdateProfile(HealthProfile profile, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 👉 check if profile already exists
        HealthProfile existing = healthProfileRepository.findByUser(user)
                .orElse(new HealthProfile());

        existing.setUser(user);
        existing.setAge(profile.getAge());
        existing.setGender(profile.getGender());
        existing.setHeightCm(profile.getHeightCm());
        existing.setWeightKg(profile.getWeightKg());
        existing.setActivityLevel(profile.getActivityLevel());
        existing.setGoal(profile.getGoal());

        // 🔥 COMPUTE BMI
        BigDecimal heightMeters = profile.getHeightCm().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal bmi = profile.getWeightKg().divide(heightMeters.pow(2), 2, RoundingMode.HALF_UP);
        existing.setBmi(bmi);

        // 🔥 COMPUTE CALORIES
        int calories = calculateCalories(profile);
        existing.setDailyCalories(calories);

        // 🔥 HEALTH SCORE (simple for now)
        // existing.setHealthScore(80);
        // ✅ FIXED HEALTH SCORE (NEW)
        existing.setHealthScore(calculateHealthScore(existing, user));

        return healthProfileRepository.save(existing);
    }

    @Override
    public HealthProfile getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return healthProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    // ================= CALCULATION =================

    private int calculateCalories(HealthProfile profile) {

        double weight = profile.getWeightKg().doubleValue();
        double height = profile.getHeightCm().doubleValue();
        int age = profile.getAge();

        double bmr;

        if (profile.getGender().equalsIgnoreCase("female")) {
            bmr = 10 * weight + 6.25 * height - 5 * age - 161;
        } else {
            bmr = 10 * weight + 6.25 * height - 5 * age + 5;
        }

        double activityFactor = switch (profile.getActivityLevel().toLowerCase()) {
            case "light" -> 1.375;
            case "moderate" -> 1.55;
            case "active" -> 1.725;
            default -> 1.2;
        };

        double calories = bmr * activityFactor;

        switch (profile.getGoal().toLowerCase()) {
            case "lose" -> calories -= 300;
            case "gain" -> calories += 300;
        }

        return (int) calories;
    }

    public HealthProfileDTO toDTO(HealthProfile profile) {

        HealthProfileDTO dto = new HealthProfileDTO();

        dto.setId(profile.getId());
        dto.setAge(profile.getAge());
        dto.setWeightKg(profile.getWeightKg());
        dto.setHeightCm(profile.getHeightCm());
        dto.setGoal(profile.getGoal());
        dto.setGender(profile.getGender());
        dto.setBmi(profile.getBmi());
        dto.setDailyCalories(profile.getDailyCalories());
        dto.setActivityLevel(profile.getActivityLevel());
        dto.setHealthScore(profile.getHealthScore());
        return dto;
    }

    private int calculateHealthScore(HealthProfile profile, User user) {

        int score = 100;

        // BMI (convert BigDecimal → double)
        double bmi = profile.getBmi().doubleValue();

        if (bmi < 18.5) score -= 10;
        else if (bmi <= 24.9) score += 5;
        else if (bmi <= 29.9) score -= 10;
        else score -= 20;

        // Age
        if (profile.getAge() > 50) score -= 10;
        else if (profile.getAge() > 35) score -= 5;

        // Activity
        switch (profile.getActivityLevel().toLowerCase()) {
            case "active" -> score += 10;
            case "moderate" -> score += 5;
            case "light" -> score += 2;
            default -> score -= 5;
        }

        // Diseases
        if (user.getDiseases() != null)
            score -= user.getDiseases().size() * 10;

        // Allergies
        if (user.getAllergies() != null)
            score -= user.getAllergies().size() * 5;

        // Goal
        switch (profile.getGoal().toLowerCase()) {
            case "maintain" -> score += 5;
            case "lose" -> score += 3;
            case "gain" -> score -= 2;
        }

        return Math.max(0, Math.min(100, score));
    }

}