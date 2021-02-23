package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.IngredientDto;
import com.example.inventoryservice.dto.IngredientUpdateAmountDto;
import com.example.inventoryservice.dto.IngredientUpdateDto;
import com.example.inventoryservice.dto.IngredientUpdatePriceDto;

public interface IngredientCommandService {
    IngredientDto create(IngredientDto ingredientDto);

    IngredientDto updateAmount(IngredientUpdateAmountDto ingredientDto);

    IngredientDto updatePrice(IngredientUpdatePriceDto ingredientDto);

    IngredientDto update(IngredientUpdateDto ingredientDto);
}
