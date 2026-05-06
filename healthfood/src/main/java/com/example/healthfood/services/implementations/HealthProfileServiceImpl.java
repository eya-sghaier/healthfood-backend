package com.example.healthfood.services.implementations;

import com.example.healthfood.DAO.entities.core.HealthProfile;
import com.example.healthfood.DAO.entities.core.User;
import com.example.healthfood.DAO.repositories.HealthProfileRepository;
import com.example.healthfood.DAO.repositories.UserRepository;
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
        existing.setHealthScore(80);

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
}