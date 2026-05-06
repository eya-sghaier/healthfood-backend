package com.example.healthfood.DAO.repositories;

import com.example.healthfood.DAO.entities.intelligence.IngredientCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientConditionRepository extends JpaRepository<IngredientCondition, Long> {

    List<IngredientCondition> findByConditionTypeAndConditionId(String type, Long id);
}