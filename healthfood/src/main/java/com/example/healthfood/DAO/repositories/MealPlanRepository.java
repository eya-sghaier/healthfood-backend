package com.example.healthfood.DAO.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.healthfood.DAO.entities.meal.MealPlan;
import java.util.List;
public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    List<MealPlan> findByUserId(Long userId);
}
