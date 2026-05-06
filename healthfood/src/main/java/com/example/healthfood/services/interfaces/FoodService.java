package com.example.healthfood.services.interfaces;

import com.example.healthfood.DAO.entities.core.Food;

import java.util.List;

public interface FoodService {

    List<Food> getAllFoods();
}