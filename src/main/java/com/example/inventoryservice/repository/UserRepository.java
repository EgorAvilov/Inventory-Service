package com.example.inventoryservice.repository;

import com.example.inventoryservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findAllByUsernameIgnoreCase(String username);

    List<User> findAllByRestaurant_Id(Long restaurantId);
}
