package com.example.healthfood.services.interfaces;

import com.example.healthfood.DAO.entities.core.Food;
import java.util.List;

public interface FavoriteService {

    void addFavorite(String email, Long foodId);

    void removeFavorite(String email, Long foodId);

    List<Food> getFavorites(String email);
}