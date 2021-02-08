package com.example.inventoryservice.repository;

import com.example.inventoryservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(value = "select count(u) from User u where u.username=?1")
    Long findAllByUsername(String username);

    List<User> findAllByRestaurantId(Long restaurantId);
}
