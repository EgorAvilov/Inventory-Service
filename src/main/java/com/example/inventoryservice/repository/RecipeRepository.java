package com.example.inventoryservice.repository;

import com.example.inventoryservice.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Long countAllByNameAndRestaurantId(String name, Long restaurantId);

    List<Recipe> findAllByRestaurantId(Long restaurantId);

    Optional<Recipe> findByNameAndRestaurantId(String name, Long id);
}

