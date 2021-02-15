package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.IngredientDto;

import java.util.List;

public interface IngredientQueryService {
    List<IngredientDto> findAllByRestaurant();
}
