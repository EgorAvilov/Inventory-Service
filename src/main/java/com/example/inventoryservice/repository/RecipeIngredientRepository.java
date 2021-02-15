package com.example.inventoryservice.repository;

import com.example.inventoryservice.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<RecipeIngredient> findAllByIdIn(List<Long> ids);
}
