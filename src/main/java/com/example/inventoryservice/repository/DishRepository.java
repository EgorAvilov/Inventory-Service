package com.example.inventoryservice.repository;


import com.example.inventoryservice.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findAllByRestaurantId(Long restaurantId);
}

