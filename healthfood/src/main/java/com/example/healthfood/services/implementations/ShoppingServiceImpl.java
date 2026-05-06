package com.example.healthfood.services.implementations;

import com.example.healthfood.DAO.entities.core.*;
import com.example.healthfood.DTO.*;
import com.example.healthfood.services.interfaces.MealPlanService;
import com.example.healthfood.services.interfaces.ShoppingService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ShoppingServiceImpl implements ShoppingService {

    private final MealPlanService mealPlanService;

    @Override
    public List<ShoppingListItemDTO> generateShoppingList(String email) {

        List<MealPlanResponseDTO> plan =
                mealPlanService.generateWeeklyPlan(email);

        Map<String, ShoppingListItemDTO> map = new HashMap<>();

        for (MealPlanResponseDTO day : plan) {

            for (MealDTO meal : day.getMeals()) {

                Food food = meal.getFood();

                for (FoodIngredient fi : food.getIngredients()) {

                    String name = fi.getIngredient().getName();
                    double qty = fi.getQuantity().doubleValue();
                    String unit = fi.getUnit();

                    if (map.containsKey(name)) {
                        ShoppingListItemDTO existing = map.get(name);
                        existing.setTotalQuantity(
                                existing.getTotalQuantity() + qty
                        );
                    } else {
                        map.put(name,
                                new ShoppingListItemDTO(name, qty, unit));
                    }
                }
            }
        }

        return new ArrayList<>(map.values());
    }
}