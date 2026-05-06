package com.example.healthfood.DAO.entities.core;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Integer calories;

    private BigDecimal protein;
    private BigDecimal carbs;
    private BigDecimal fat;
    private BigDecimal fiber;
    private BigDecimal sugar;
    private BigDecimal sodium;

    private String imageUrl;

    private Integer prepTime;

    private Integer servings;

    // FOOD ↔ INGREDIENTS (many-to-many via join table)
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<FoodIngredient> ingredients;

    // FOOD ↔ BADGES
    @ManyToMany
    @JsonIgnore
    @JoinTable(
        name = "food_badges",
        joinColumns = @JoinColumn(name = "food_id"),
        inverseJoinColumns = @JoinColumn(name = "badge_id")
    )
    private List<Badge> badges;
}