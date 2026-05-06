package com.example.healthfood.DAO.entities.core;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "food_ingredients")
public class FoodIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "food_id")
    @JsonIgnore
    private Food food;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    @JsonIgnore
    private Ingredient ingredient;

    private BigDecimal quantity;

    private String unit;
}