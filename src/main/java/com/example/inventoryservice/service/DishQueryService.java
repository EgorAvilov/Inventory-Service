package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.DishDto;

import java.util.List;

public interface DishQueryService {

    List<DishDto> findAllByRestaurant();
}
