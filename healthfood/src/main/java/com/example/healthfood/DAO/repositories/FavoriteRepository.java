package com.example.healthfood.DAO.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.healthfood.DAO.entities.userdata.Favorite;
import com.example.healthfood.DAO.entities.userdata.FavoriteId;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {

    List<Favorite> findByUser_Id(Long userId);

    boolean existsByUser_IdAndFood_Id(Long userId, Long foodId);

    void deleteByUser_IdAndFood_Id(Long userId, Long foodId);
}