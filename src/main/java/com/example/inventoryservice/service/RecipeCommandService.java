package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.RecipeCreateDto;
import com.example.inventoryservice.dto.RecipeDto;

public interface RecipeCommandService {
    RecipeDto create(RecipeCreateDto recipeDto);
}
