package com.example.healthfood.controllers;

import com.example.healthfood.DTO.ShoppingListItemDTO;
import com.example.healthfood.services.interfaces.ShoppingService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping")
@RequiredArgsConstructor
public class ShoppingController {

    private final ShoppingService shoppingService;

    @GetMapping
    public List<ShoppingListItemDTO> getShoppingList(Authentication authentication) {

        String email = authentication.getName();

        return shoppingService.generateShoppingList(email);
    }
}