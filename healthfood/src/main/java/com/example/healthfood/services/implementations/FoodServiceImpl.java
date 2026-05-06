package com.example.healthfood.services.implementations;

import com.example.healthfood.DAO.entities.core.Food;
import com.example.healthfood.DAO.repositories.FoodRepository;
import com.example.healthfood.services.interfaces.FoodService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    @Override
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }
}