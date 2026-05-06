package com.example.healthfood.DAO.repositories;

import com.example.healthfood.DAO.entities.intelligence.HealthTip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthTipRepository extends JpaRepository<HealthTip, Long> {
}