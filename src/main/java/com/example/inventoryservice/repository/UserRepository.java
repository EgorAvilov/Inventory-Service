package com.example.inventoryservice.repository;

import com.example.inventoryservice.entity.Role;
import com.example.inventoryservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Long countAllByUsername(String username);

    List<User> findAllByRestaurantId(Long restaurantId);

    List<User> findAllByUserRoleAndRestaurantId(Role role, Long restaurantId);
}
