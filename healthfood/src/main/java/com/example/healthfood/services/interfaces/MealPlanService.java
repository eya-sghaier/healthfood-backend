package com.example.healthfood.services.interfaces;
import com.example.healthfood.DTO.MealPlanResponseDTO;
import java.util.List;

public interface MealPlanService {
    List<MealPlanResponseDTO> generateWeeklyPlan(String email);
    // NEW
    void saveGeneratedPlan(String email, List<MealPlanResponseDTO> plan);

}
