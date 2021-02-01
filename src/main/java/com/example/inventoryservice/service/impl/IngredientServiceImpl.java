package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.IngredientConverter;
import com.example.inventoryservice.converter.RecipeConverter;
import com.example.inventoryservice.dto.IngredientDto;
import com.example.inventoryservice.dto.RecipeDto;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.entity.Recipe;
import com.example.inventoryservice.repository.IngredientRepository;
import com.example.inventoryservice.repository.RecipeRepository;
import com.example.inventoryservice.service.IngredientService;
import com.example.inventoryservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientConverter ingredientConverter;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientConverter ingredientConverter, UserService userService) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientConverter = ingredientConverter;
        this.userService = userService;
    }

    @Override
    public IngredientDto create(IngredientDto ingredientDto) {
        logger.info("Create ingredient");
        Ingredient ingredient = ingredientConverter.dtoToEntity(ingredientDto);
        Ingredient persistIngredient = ingredientRepository.save(ingredient);
        return ingredientConverter.entityToDto(persistIngredient);
    }

    @Override
    public List<IngredientDto> findAllByRestaurant() {
        logger.info("Find all ingredients by restaurant");
        UserDto userDto = userService.getCurrentUser();
        List<Ingredient> ingredients = ingredientRepository.findAllByRestaurant_Id(userDto.getRestaurant()
                                                                              .getId());
        return ingredientConverter.entityToDto(ingredients);
    }
}
