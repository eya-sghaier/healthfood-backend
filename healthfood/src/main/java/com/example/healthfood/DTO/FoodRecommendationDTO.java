package com.example.healthfood.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodRecommendationDTO {

    private Long foodId;
    private String name;
    private String imageUrl;
    private Integer calories;
    private int score;
}