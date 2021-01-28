package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.dto.RecipeDto;
import com.example.inventoryservice.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Override
    public RecipeDto create(RecipeDto recipeDto) {
        return null;
    }

    @Override
    public List<RecipeDto> findAllByRestaurant() {
        return null;
    }
}
