package com.example.healthfood.DAO.repositories;

import com.example.healthfood.DAO.entities.core.HealthProfile;
import com.example.healthfood.DAO.entities.core.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthProfileRepository extends JpaRepository<HealthProfile, Long> {

    Optional<HealthProfile> findByUser(User user);
}