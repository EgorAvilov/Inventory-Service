package com.example.inventoryservice.repository;

import com.example.inventoryservice.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByNameIgnoreCaseAndRestaurant_Id(String name,Long restaurantId);

    List<Recipe> findAllByRestaurant_Id(Long restaurantId);
}

