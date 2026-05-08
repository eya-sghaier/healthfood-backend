package com.example.healthfood.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthProfileDTO {
    public Long id;
    public Integer age;
    public BigDecimal weightKg;
    public BigDecimal heightCm;
    public String goal;
    public String gender;
    public BigDecimal bmi;
    public Integer dailyCalories;
    public String activityLevel;
    public Integer healthScore;
}
