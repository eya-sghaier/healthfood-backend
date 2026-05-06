package com.example.healthfood.DAO.repositories;

import com.example.healthfood.DAO.entities.core.FoodMealType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodMealTypeRepository extends JpaRepository<FoodMealType, Long> {

    List<FoodMealType> findByMealType_Name(String name);
}