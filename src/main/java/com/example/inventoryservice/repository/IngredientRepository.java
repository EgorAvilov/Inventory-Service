package com.example.inventoryservice.repository;

import com.example.inventoryservice.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findAllByRestaurantId(Long restaurantId);

    Long countAllByNameAndRestaurantId(String name, Long restaurantId);

    Optional<Ingredient> findByNameAndRestaurantId(String name,Long restaurantId);

    List<Ingredient> findAllByNameIn(List<String> names);
}

