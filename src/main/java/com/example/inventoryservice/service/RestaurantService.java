package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.RestaurantDto;

public interface RestaurantService {
    RestaurantDto create(RestaurantDto restaurantDto);

    boolean restaurantExists(String name);
}
