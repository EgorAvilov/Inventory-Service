package com.example.inventoryservice.repository;

import com.example.inventoryservice.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findAllByRestaurantId(Long restaurantId);

    @Query(value = "select count(i) from Ingredient i where i.name=?1 and i.restaurant.id=?2")
    Long findAllByNameAndRestaurantId(String name, Long restaurantId);
}

