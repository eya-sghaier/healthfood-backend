package com.example.healthfood.DAO.entities.meal;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import com.example.healthfood.DAO.entities.core.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "meal_plan_items")
public class MealPlanItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MealPlan mealPlan;

    private LocalDate dayDate;

    @ManyToOne
    private MealType mealType;

    @ManyToOne
    private Food food;

    private Boolean isConsumed;
}