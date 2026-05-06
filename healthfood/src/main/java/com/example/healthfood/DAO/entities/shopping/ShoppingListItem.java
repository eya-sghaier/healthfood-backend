package com.example.healthfood.DAO.entities.shopping;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import com.example.healthfood.DAO.entities.core.Ingredient;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "shopping_list_items")
public class ShoppingListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ShoppingList shoppingList;

    @ManyToOne
    private Ingredient ingredient;

    private BigDecimal totalQuantity;

    private String unit;

    private Boolean isPurchased;

    private BigDecimal estimatedCost;
}