package com.example.healthfood.DAO.entities.core;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "health_profiles")
public class HealthProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    private Integer age;

    private String gender;

    private BigDecimal heightCm;

    private BigDecimal weightKg;

    private String activityLevel;

    private String goal;

    private Integer healthScore;

    private Integer dailyCalories;

    private BigDecimal bmi;

    private LocalDateTime lastUpdated = LocalDateTime.now();
}