package com.example.healthfood.services.implementations;

import com.example.healthfood.DAO.entities.core.*;
import com.example.healthfood.DAO.entities.reference.*;
import com.example.healthfood.DAO.entities.intelligence.IngredientCondition;
import com.example.healthfood.DAO.repositories.*;
import com.example.healthfood.DTO.FoodRecommendationDTO;
import com.example.healthfood.services.interfaces.RecommendationService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final IngredientConditionRepository conditionRepository;
    

    @Override
    public List<FoodRecommendationDTO> getRecommendedFoods(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Food> allFoods = foodRepository.findAll();
        List<FoodRecommendationDTO> result = new ArrayList<>();
        HealthProfile profile = user.getHealthProfile();

        for (Food food : allFoods) {

            boolean excluded = false;
            int score = 50;

            for (FoodIngredient fi : food.getIngredients()) {

                Long ingredientId = fi.getIngredient().getId();

                // 🔴 ALLERGIES
                for (Allergy allergy : user.getAllergies()) {
                    List<IngredientCondition> conditions =
                            conditionRepository.findByConditionTypeAndConditionId("allergy", allergy.getId());

                    for (IngredientCondition c : conditions) {
                        if (c.getIngredient().getId().equals(ingredientId)
                                && c.getRuleType().equals("avoid")) {
                            excluded = true;
                        }
                    }
                }

                // 🔴 INTOLERANCES
                for (Intolerance intolerance : user.getIntolerances()) {
                    List<IngredientCondition> conditions =
                            conditionRepository.findByConditionTypeAndConditionId("intolerance", intolerance.getId());

                    for (IngredientCondition c : conditions) {
                        if (c.getIngredient().getId().equals(ingredientId)
                                && c.getRuleType().equals("avoid")) {
                            excluded = true;
                        }
                    }
                }

                // 🟡 DISEASES
                for (Disease disease : user.getDiseases()) {
                    List<IngredientCondition> conditions =
                            conditionRepository.findByConditionTypeAndConditionId("disease", disease.getId());

                    for (IngredientCondition c : conditions) {
                        if (c.getIngredient().getId().equals(ingredientId)
                                && c.getRuleType().equals("limit")) {
                            score -= 20;
                        }
                    }
                }

                // 🟢 PREFERENCES (simple boost)
                for (DietaryPreference pref : user.getPreferences()) {

                    if (pref.getName().equalsIgnoreCase("Vegetarian")) {
                        if (!food.getName().toLowerCase().contains("chicken")
                                && !food.getName().toLowerCase().contains("salmon")) {
                            score += 10;
                        }
                    }

                    if (pref.getName().equalsIgnoreCase("Vegan")) {
                        score += 8;
                    }

                    if (pref.getName().equalsIgnoreCase("Halal")) {
                        score += 5;
                    }
                    
                }


                if (profile != null && profile.getGoal() != null) {

                    String goal = profile.getGoal().toLowerCase();

                    // 🟡 WEIGHT LOSS LOGIC
                    if (goal.contains("lose")) {
                        if (food.getCalories() < 350) {
                            score += 20;
                        } else {
                            score -= 10;
                        }

                        if (food.getSugar().compareTo(BigDecimal.valueOf(15)) > 0) {
                            score -= 10;
                        }
                    }

                    // 🟢 MUSCLE GAIN LOGIC
                    if (goal.contains("gain")) {
                        if (food.getProtein().compareTo(BigDecimal.valueOf(20)) > 0) {
                            score += 20;
                        }
                    }
                    
                }
            }

            if (!excluded) {
                result.add(new FoodRecommendationDTO(
                    food.getId(),
                    food.getName(),
                    food.getImageUrl(),
                    food.getCalories(),
                    score
            ));
            }
        }

        // 🔥 SORT (best first)
        result.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

        return result;
    }
}