package com.example.healthfood.controllers;
import com.example.healthfood.DTO.MealPlanResponseDTO;
import com.example.healthfood.services.interfaces.MealPlanService;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/meal-plans")
@RequiredArgsConstructor
public class MealPlanController {

    private final MealPlanService mealPlanService;

    @GetMapping("/generate")
    public List<MealPlanResponseDTO> generate(Authentication authentication) {

        String email = authentication.getName();

        return mealPlanService.generateWeeklyPlan(email);
    }
    @PostMapping("/save")
    public String savePlan(Authentication auth,
                        @RequestBody List<MealPlanResponseDTO> plan) {

        mealPlanService.saveGeneratedPlan(auth.getName(), plan);
        return "Plan saved successfully";
    }
}