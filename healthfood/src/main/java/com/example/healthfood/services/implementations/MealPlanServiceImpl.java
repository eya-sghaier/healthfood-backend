package com.example.healthfood.services.implementations;

import com.example.healthfood.DAO.entities.core.Food;
import com.example.healthfood.DAO.entities.core.FoodMealType;
import com.example.healthfood.DAO.entities.core.MealType;
import com.example.healthfood.DAO.entities.core.User;
import com.example.healthfood.DAO.entities.meal.MealPlan;
import com.example.healthfood.DAO.entities.meal.MealPlanItem;
import com.example.healthfood.DAO.repositories.FoodMealTypeRepository;
import com.example.healthfood.DAO.repositories.MealPlanItemRepository;
import com.example.healthfood.DAO.repositories.MealPlanRepository;
import com.example.healthfood.DAO.repositories.MealTypeRepository;
import com.example.healthfood.DAO.repositories.UserRepository;
import com.example.healthfood.DTO.FoodRecommendationDTO;
import com.example.healthfood.DTO.MealDTO;
import com.example.healthfood.DTO.MealPlanResponseDTO;
import com.example.healthfood.services.interfaces.MealPlanService;
import com.example.healthfood.services.interfaces.RecommendationService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MealPlanServiceImpl implements MealPlanService {

    private final UserRepository userRepository;
    private final FoodMealTypeRepository foodMealTypeRepository;
    private final RecommendationService recommendationService;
    private final MealPlanRepository mealPlanRepository;
    private final MealPlanItemRepository mealPlanItemRepository;
    private final MealTypeRepository mealTypeRepository;

    private final Random random = new Random();

    @Override
    public List<MealPlanResponseDTO> generateWeeklyPlan(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 GET SMART RECOMMENDATIONS (already filtered + scored)
        List<FoodRecommendationDTO> recommended =
                recommendationService.getRecommendedFoods(email);

        // 🧠 Convert to Map for fast access
        Map<Long, Integer> scoreMap = new HashMap<>();
        for (FoodRecommendationDTO dto : recommended) {
            scoreMap.put(dto.getFoodId(), dto.getScore());
        }

        // 🔽 GET FOODS BY MEAL TYPE (via FoodMealType)
        List<Food> breakfasts = getFoodsByType("breakfast");
        List<Food> lunches = getFoodsByType("lunch");
        List<Food> dinners = getFoodsByType("dinner");
        List<Food> snacks = getFoodsByType("snack");

        List<MealPlanResponseDTO> weekPlan = new ArrayList<>();

        Set<Long> usedWeekFoods = new HashSet<>();

        String[] days = {
                "MONDAY", "TUESDAY", "WEDNESDAY",
                "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"
        };

        for (String day : days) {

            Set<Long> usedDayFoods = new HashSet<>();

            List<MealDTO> meals = new ArrayList<>();

            meals.add(new MealDTO("BREAKFAST",
                    pickSmartFood(breakfasts, scoreMap, usedWeekFoods, usedDayFoods)));

            meals.add(new MealDTO("LUNCH",
                    pickSmartFood(lunches, scoreMap, usedWeekFoods, usedDayFoods)));

            meals.add(new MealDTO("DINNER",
                    pickSmartFood(dinners, scoreMap, usedWeekFoods, usedDayFoods)));

            meals.add(new MealDTO("SNACK",
                    pickSmartFood(snacks, scoreMap, usedWeekFoods, usedDayFoods)));

            weekPlan.add(new MealPlanResponseDTO(day, meals));
        }

        return weekPlan;
    }

    // 🔽 GET FOODS FROM FoodMealType
    private List<Food> getFoodsByType(String type) {
        return foodMealTypeRepository
                .findByMealType_Name(type)
                .stream()
                .map(FoodMealType::getFood)
                .toList();
    }

    // 🔥 SMART PICK (V2)
    private Food pickSmartFood(List<Food> foods,
                               Map<Long, Integer> scoreMap,
                               Set<Long> usedWeekFoods,
                               Set<Long> usedDayFoods) {

        List<Food> candidates = foods.stream()
                .filter(f -> scoreMap.containsKey(f.getId())) // only recommended
                .filter(f -> !usedDayFoods.contains(f.getId()))
                .filter(f -> !usedWeekFoods.contains(f.getId()))
                .toList();

        if (candidates.isEmpty()) {
            candidates = foods; // fallback
        }

        // 🔥 SORT BY SCORE DESC
        candidates = candidates.stream()
                .sorted((a, b) -> Integer.compare(
                        scoreMap.getOrDefault(b.getId(), 0),
                        scoreMap.getOrDefault(a.getId(), 0)
                ))
                .toList();

        // 🎯 PICK FROM TOP 3 (not always same)
        int limit = Math.min(3, candidates.size());
        Food selected = candidates.get(random.nextInt(limit));

        usedDayFoods.add(selected.getId());
        usedWeekFoods.add(selected.getId());

        return selected;
    }

    @Override
        public void saveGeneratedPlan(String email, List<MealPlanResponseDTO> plan) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        MealPlan mealPlan = new MealPlan();
        mealPlan.setUser(user);
        mealPlan.setName("My Weekly Plan");
        mealPlan.setStartDate(LocalDate.now());
        mealPlan.setEndDate(LocalDate.now().plusDays(6));
        mealPlan.setIsActive(true);

        mealPlan = mealPlanRepository.save(mealPlan);

        for (MealPlanResponseDTO dayPlan : plan) {

                LocalDate date = LocalDate.now().plusDays(
                        Arrays.asList("MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY")
                                .indexOf(dayPlan.getDay())
                );

                for (MealDTO meal : dayPlan.getMeals()) {

                MealPlanItem item = new MealPlanItem();

                item.setMealPlan(mealPlan);
                item.setDayDate(date);
                item.setFood(meal.getFood());
                item.setIsConsumed(false);

                // ⚠️ IMPORTANT → you need MealType here
                MealType mealType = mealTypeRepository.findByName(meal.getType().toLowerCase()).orElseThrow(() -> new RuntimeException("MealType not found"));

                mealPlanItemRepository.save(item);
                }
        }
        }
}