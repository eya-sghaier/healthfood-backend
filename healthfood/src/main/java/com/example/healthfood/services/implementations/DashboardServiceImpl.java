package com.example.healthfood.services.implementations;

import com.example.healthfood.DAO.entities.core.User;
import com.example.healthfood.DAO.entities.core.HealthProfile;
import com.example.healthfood.DAO.entities.meal.MealPlan;
import com.example.healthfood.DAO.entities.intelligence.HealthTip;
import com.example.healthfood.DAO.repositories.UserRepository;
import com.example.healthfood.DAO.repositories.MealPlanRepository;
import com.example.healthfood.DAO.repositories.HealthTipRepository;
import com.example.healthfood.DTO.DashboardDTO;
import com.example.healthfood.DTO.FoodRecommendationDTO;
import com.example.healthfood.services.interfaces.DashboardService;
import com.example.healthfood.services.interfaces.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final RecommendationService recommendationService;
    private final MealPlanRepository mealPlanRepository;
    private final HealthTipRepository healthTipRepository;

    @Override
    public DashboardDTO getDashboard(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        HealthProfile profile = user.getHealthProfile();

        // 🟢 BASIC INFO
        BigDecimal bmi = profile != null ? profile.getBmi() : null;
        Integer calories = profile != null ? profile.getDailyCalories() : null;
        Integer score = profile != null ? profile.getHealthScore() : null;
        String goal = profile != null ? profile.getGoal() : null;

        // 🍽️ TOP RECOMMENDATIONS
        List<FoodRecommendationDTO> topFoods =
                recommendationService.getRecommendedFoods(email)
                        .stream()
                        .limit(5)
                        .toList();

        // 📅 ACTIVE PLAN
        List<MealPlan> plans = mealPlanRepository.findByUserId(user.getId());

        String activePlanMessage = null;

        for (MealPlan plan : plans) {
            if (Boolean.TRUE.equals(plan.getIsActive())) {
                activePlanMessage =
                        "Your plan is active — ends on " + plan.getEndDate();
                break;
            }
        }

        // 💡 HEALTH TIPS
        List<String> tips = healthTipRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(HealthTip::getPriority))
                .limit(2)
                .map(HealthTip::getTipText)
                .toList();

        return new DashboardDTO(
                bmi,
                calories,
                score,
                goal,
                topFoods,
                activePlanMessage,
                tips
        );
    }
}