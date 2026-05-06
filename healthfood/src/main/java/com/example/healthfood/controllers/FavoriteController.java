package com.example.healthfood.controllers;

import com.example.healthfood.DAO.entities.core.Food;
import com.example.healthfood.services.interfaces.FavoriteService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    // ⭐ ADD FAVORITE
    @PostMapping("/{foodId}")
    public String addFavorite(Authentication auth,
                              @PathVariable Long foodId) {

        favoriteService.addFavorite(auth.getName(), foodId);
        return "Added to favorites";
    }

    // ❌ REMOVE FAVORITE
    @DeleteMapping("/{foodId}")
    public String removeFavorite(Authentication auth,
                                 @PathVariable Long foodId) {

        favoriteService.removeFavorite(auth.getName(), foodId);
        return "Removed from favorites";
    }

    // 📋 GET FAVORITES
    @GetMapping
    public List<Food> getFavorites(Authentication auth) {

        return favoriteService.getFavorites(auth.getName());
    }
}