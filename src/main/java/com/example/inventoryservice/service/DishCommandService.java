package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.DishDto;
import com.example.inventoryservice.entity.Dish;

import java.util.List;

public interface DishCommandService {

    DishDto create(DishDto dishDto);

    void cookingDish(Dish dish);
}
