package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.RecipeCreateDto;
import com.example.inventoryservice.dto.RecipeDto;
import com.example.inventoryservice.dto.RecipeUpdateDto;

public interface RecipeCommandService {
    RecipeDto create(RecipeCreateDto recipeDto);

    RecipeDto update(RecipeUpdateDto recipeDto);
}
