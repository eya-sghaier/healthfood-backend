package com.example.healthfood.DTO;

import java.util.List;

import lombok.*;

@Data
@AllArgsConstructor
public class MealPlanResponseDTO {

    private String day;
    private List<MealDTO> meals;

}