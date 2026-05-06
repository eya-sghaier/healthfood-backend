package com.example.healthfood.DTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {

    private BigDecimal bmi;
    private Integer dailyCalories;
    private Integer healthScore;
    private String goal;

    private List<FoodRecommendationDTO> topRecommendations;

    private String activePlanMessage;

    private List<String> healthTips;
}