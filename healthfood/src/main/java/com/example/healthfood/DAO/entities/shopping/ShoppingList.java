package com.example.healthfood.DAO.entities.shopping;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import com.example.healthfood.DAO.entities.core.User;
import com.example.healthfood.DAO.entities.meal.MealPlan;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "shopping_lists")
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private MealPlan mealPlan;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL)
    private List<ShoppingListItem> items;
}