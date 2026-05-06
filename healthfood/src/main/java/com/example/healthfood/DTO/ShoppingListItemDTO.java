package com.example.healthfood.DTO;

import lombok.*;

@Data
@AllArgsConstructor
public class ShoppingListItemDTO {

    private String ingredientName;
    private double totalQuantity;
    private String unit;
}