package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.IngredientDto;
import com.example.inventoryservice.entity.Ingredient;

import java.util.List;

public interface IngredientService {
    IngredientDto create(IngredientDto ingredientDto);

    IngredientDto update(IngredientDto ingredientDto);

    List<IngredientDto> findAllByRestaurant();

    boolean ingredientExists(Ingredient ingredient);
}
