package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.DishDto;
import com.example.inventoryservice.dto.IngredientDto;
import com.example.inventoryservice.entity.Dish;

import java.util.List;

public interface DishService {
    DishDto create(DishDto dishDto);

    List<DishDto> findAllByRestaurant();

    void checkForEnoughIngredients(Dish dish);
}
