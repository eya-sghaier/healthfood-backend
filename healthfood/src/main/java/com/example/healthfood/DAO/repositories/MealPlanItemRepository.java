package com.example.healthfood.DAO.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.healthfood.DAO.entities.meal.MealPlanItem;
public interface MealPlanItemRepository extends JpaRepository<MealPlanItem, Long> {

}
