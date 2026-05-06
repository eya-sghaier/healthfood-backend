package com.example.healthfood.DAO.repositories;

import com.example.healthfood.DAO.entities.core.Food;
import org.springframework.data.jpa.repository.JpaRepository;
//import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
   // List<Food> findByMealTypes_Name(String name);
}