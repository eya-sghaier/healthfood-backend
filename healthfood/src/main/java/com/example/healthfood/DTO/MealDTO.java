package com.example.healthfood.DTO;
import lombok.*;
import com.example.healthfood.DAO.entities.core.Food;

@Data
@AllArgsConstructor
public class MealDTO {

    private String type; // BREAKFAST / LUNCH / DINNER / SNACK
    private Food food;

}