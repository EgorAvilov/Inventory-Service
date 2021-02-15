package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.RecipeDto;

public interface RecipeCommandService {
    RecipeDto create(RecipeDto recipeDto);
}
