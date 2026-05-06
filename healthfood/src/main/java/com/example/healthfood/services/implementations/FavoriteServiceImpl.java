package com.example.healthfood.services.implementations;

import com.example.healthfood.DAO.entities.core.*;
import com.example.healthfood.DAO.entities.userdata.*;
import com.example.healthfood.DAO.repositories.*;

import com.example.healthfood.services.interfaces.FavoriteService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    @Override
    public void addFavorite(String email, Long foodId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (favoriteRepository.existsByUser_IdAndFood_Id(user.getId(), foodId)) {
            return; // already exists
        }

        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        FavoriteId id = new FavoriteId(user.getId(), foodId);

        Favorite favorite = new Favorite();
        favorite.setId(id);
        favorite.setUser(user);
        favorite.setFood(food);

        favoriteRepository.save(favorite);
    }

    @Override
    @Transactional
    public void removeFavorite(String email, Long foodId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        favoriteRepository.deleteByUser_IdAndFood_Id(user.getId(), foodId);
    }

    @Override
    public List<Food> getFavorites(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return favoriteRepository.findByUser_Id(user.getId())
                .stream()
                .map(Favorite::getFood)
                .toList();
    }
}