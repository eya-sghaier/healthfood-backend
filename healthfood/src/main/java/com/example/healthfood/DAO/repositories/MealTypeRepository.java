package com.example.healthfood.DAO.repositories;

import com.example.healthfood.DAO.entities.core.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MealTypeRepository extends JpaRepository<MealType, Long> {
    Optional<MealType> findByName(String name);
}