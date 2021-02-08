package com.example.inventoryservice.repository;

import com.example.inventoryservice.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query(value = "select count(r) from Recipe r where r.name=?1 and r.restaurant.id=?2")
    Long findAllByNameAndRestaurantId(String name, Long restaurantId);

    List<Recipe> findAllByRestaurantId(Long restaurantId);
}

