package com.example.inventoryservice.service;

import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.entity.User;

import java.util.List;
import java.util.Map;

public interface NotificationService {

    public List<Ingredient> remind(Long restaurantId);
}
