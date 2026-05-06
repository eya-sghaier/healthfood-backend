package com.example.healthfood.DAO.entities.intelligence;

import jakarta.persistence.*;
import lombok.*;
import com.example.healthfood.DAO.entities.core.Ingredient;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "ingredient_conditions")
public class IngredientCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String conditionType; // disease / allergy / intolerance

    private Long conditionId;

    @ManyToOne
    private Ingredient ingredient;

    private String ruleType; // avoid / recommended / limit

    private String description;
}