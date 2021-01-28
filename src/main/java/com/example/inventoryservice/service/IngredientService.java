package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.IngredientDto;

import java.util.List;

public interface IngredientService {
    IngredientDto create(IngredientDto ingredientDto);

    List<IngredientDto> findAllByRestaurant();
}
