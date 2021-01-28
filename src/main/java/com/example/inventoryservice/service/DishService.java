package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.DishDto;
import com.example.inventoryservice.dto.IngredientDto;

import java.util.List;

public interface DishService {
    DishDto create(DishDto dishDto);

    List<DishDto> findAllByRestaurant();
}
