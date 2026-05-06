package com.example.healthfood.controllers;

import com.example.healthfood.DAO.entities.core.Food;
import com.example.healthfood.DTO.FoodRecommendationDTO;
import com.example.healthfood.services.interfaces.FoodService;
import com.example.healthfood.services.interfaces.RecommendationService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    // ✅ GET ALL FOODS (protected)
    @GetMapping
    public List<Food> getAllFoods(Authentication authentication) {

        // just to confirm user is authenticated
        System.out.println("USER: " + authentication.getName());

        return foodService.getAllFoods();
    }

    private final RecommendationService recommendationService;

    @GetMapping("/recommendations")
    public List<FoodRecommendationDTO> getRecommendations(Authentication authentication) {
        String email = authentication.getName();
        return recommendationService.getRecommendedFoods(email);
    }
}