package com.example.inventoryservice.repository;

import com.example.inventoryservice.entity.Recipe;
import com.example.inventoryservice.entity.Restaurant;
import com.example.inventoryservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}

