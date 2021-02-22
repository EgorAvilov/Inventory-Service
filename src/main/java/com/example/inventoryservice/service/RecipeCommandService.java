package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.RecipeCreateDto;
import com.example.inventoryservice.dto.RecipeDto;
import com.example.inventoryservice.dto.RecipeUpdateDto;
import com.example.inventoryservice.entity.Recipe;

public interface RecipeCommandService {
    RecipeDto create(RecipeCreateDto recipeDto);

    RecipeDto update(RecipeUpdateDto recipeDto);

    boolean ingredientsRepeatInRecipe(Recipe recipe);

    boolean checkForNullAndZeroAmounts(Recipe recipe);

    boolean recipeExists(Recipe recipe);
}
