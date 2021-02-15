package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.RecipeConverter;
import com.example.inventoryservice.converter.RestaurantConverter;
import com.example.inventoryservice.dto.RecipeDto;
import com.example.inventoryservice.dto.RestaurantDto;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.entity.Recipe;
import com.example.inventoryservice.entity.RecipeIngredient;
import com.example.inventoryservice.entity.Restaurant;
import com.example.inventoryservice.exception.ServiceException;
import com.example.inventoryservice.repository.IngredientRepository;
import com.example.inventoryservice.repository.RecipeRepository;
import com.example.inventoryservice.service.RecipeCommandService;
import com.example.inventoryservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeCommandServiceImpl implements RecipeCommandService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeCommandServiceImpl.class);
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeConverter recipeConverter;
    private final RestaurantConverter restaurantConverter;
    private final UserService userService;

    @Autowired
    public RecipeCommandServiceImpl(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, RecipeConverter recipeConverter, RestaurantConverter restaurantConverter, UserService userService) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeConverter = recipeConverter;
        this.restaurantConverter = restaurantConverter;
        this.userService = userService;
    }

    @Override
    public RecipeDto create(RecipeDto recipeDto) {
        LOGGER.info("Create recipe");
        UserDto userDto = userService.getCurrentUser();
        RestaurantDto restaurantDto = userDto.getRestaurant();
        Restaurant restaurant = restaurantConverter.dtoToEntity(restaurantDto);
        Recipe recipe = recipeConverter.dtoToEntity(recipeDto);
        recipe.setRestaurant(restaurant);
        if (recipeExists(recipe)) {
            LOGGER.error("Not unique name {}", recipe.getName());
            throw new ServiceException("Name should be unique");
        }
        List<Ingredient> ingredients = recipe.getRecipeIngredients()
                                             .stream()
                                             .map(RecipeIngredient::getIngredient)
                                             .map((ingredient) -> ingredientRepository.findByName(ingredient.getName())
                                                                                      .orElse(Ingredient.builder()
                                                                                                        .name(ingredient.getName())
                                                                                                        // .restaurant(restaurant)
                                                                                                        .build()))

                                             .collect(Collectors.toList());
        for (int i = 0; i < ingredients.size(); i++) {
            recipe.getRecipeIngredients()
                  .get(i)
                  .setIngredient(ingredients.get(i));
        }
        Recipe persistRecipe = recipeRepository.save(recipe);
        return recipeConverter.entityToDto(persistRecipe);
    }

    public boolean recipeExists(Recipe recipe) {
        LOGGER.info("Check for existing recipe {}", recipe.getName());
        return recipeRepository.countAllByNameAndRestaurantId(recipe.getName(), recipe.getRestaurant()
                                                                                      .getId()) != 0;
    }
}
