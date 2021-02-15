package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.RecipeDto;

import java.util.List;

public interface RecipeQueryService {
    List<RecipeDto> findAllByRestaurant();
}
