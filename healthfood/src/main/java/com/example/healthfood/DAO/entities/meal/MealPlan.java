package com.example.healthfood.DAO.entities.meal;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.example.healthfood.DAO.entities.core.User;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "meal_plans")
public class MealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String name;

    private LocalDate startDate;
    private LocalDate endDate;

    private Boolean isActive;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "mealPlan", cascade = CascadeType.ALL)
    private List<MealPlanItem> items;
}