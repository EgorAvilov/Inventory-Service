package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.RecipeConverter;
import com.example.inventoryservice.dto.RecipeDto;
import com.example.inventoryservice.dto.RestaurantDto;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.Recipe;
import com.example.inventoryservice.exception.ServiceException;
import com.example.inventoryservice.repository.IngredientRepository;
import com.example.inventoryservice.repository.RecipeRepository;
import com.example.inventoryservice.service.RecipeService;
import com.example.inventoryservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeConverter recipeConverter;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, RecipeConverter recipeConverter, UserService userService) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeConverter = recipeConverter;
        this.userService = userService;
    }

    @Override
    public RecipeDto create(RecipeDto recipeDto) {
        logger.info("Create recipe");
        UserDto userDto = userService.getCurrentUser();
        RestaurantDto restaurantDto = userDto.getRestaurant();
        recipeDto.setRestaurant(restaurantDto);
        Recipe recipe = recipeConverter.dtoToEntity(recipeDto);
        if (recipeExists(recipe)) {
            logger.error("Not unique name {}", recipe.getName());
            throw new ServiceException("Name should be unique");
        }

        Recipe persistRecipe = recipeRepository.save(recipe);
        return recipeConverter.entityToDto(persistRecipe);
    }

    @Override
    public List<RecipeDto> findAllByRestaurant() {
        logger.info("Find all recipes by restaurant");
        UserDto userDto = userService.getCurrentUser();
        List<Recipe> recipes = recipeRepository.findAllByRestaurant_Id(userDto.getRestaurant()
                                                                              .getId());
        return recipeConverter.entityToDto(recipes);
    }

    @Override
    public boolean recipeExists(Recipe recipe) {
        logger.info("Check for existing recipe {}", recipe.getName());
        return recipeRepository.findAllByNameIgnoreCaseAndRestaurant_Id(recipe.getName(), recipe.getRestaurant()
                                                                                                .getId())
                               .size() != 0;
    }
}
