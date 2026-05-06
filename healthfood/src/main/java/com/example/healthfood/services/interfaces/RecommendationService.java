package com.example.healthfood.services.interfaces;

import com.example.healthfood.DAO.entities.core.Food;
import com.example.healthfood.DTO.FoodRecommendationDTO;

import java.util.List;

public interface RecommendationService {

    List<FoodRecommendationDTO> getRecommendedFoods(String email);
}