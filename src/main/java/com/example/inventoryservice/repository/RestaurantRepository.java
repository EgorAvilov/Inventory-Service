package com.example.inventoryservice.repository;

import com.example.inventoryservice.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query(value = "select count(r) from Restaurant r where r.name=?1")
    Long findAllByName(String name);
}

