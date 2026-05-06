package com.example.healthfood.services.interfaces;

import com.example.healthfood.DTO.ShoppingListItemDTO;
import java.util.List;

public interface ShoppingService {

    List<ShoppingListItemDTO> generateShoppingList(String email);
}