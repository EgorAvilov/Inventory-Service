package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.dto.IngredientDto;
import com.example.inventoryservice.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    @Override
    public IngredientDto create(IngredientDto ingredientDto) {
        return null;
    }

    @Override
    public List<IngredientDto> findAllByRestaurant() {
        return null;
    }
}
