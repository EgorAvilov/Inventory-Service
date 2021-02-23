package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.RecipeConverter;
import com.example.inventoryservice.converter.RestaurantConverter;
import com.example.inventoryservice.dto.*;
import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.entity.Recipe;
import com.example.inventoryservice.entity.RecipeIngredient;
import com.example.inventoryservice.entity.Restaurant;
import com.example.inventoryservice.exception.NoItemException;
import com.example.inventoryservice.exception.ServiceException;
import com.example.inventoryservice.repository.IngredientRepository;
import com.example.inventoryservice.repository.RecipeRepository;
import com.example.inventoryservice.service.RecipeCommandService;
import com.example.inventoryservice.service.UserQueryService;
import org.hibernate.validator.cfg.defs.DigitsDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RecipeCommandServiceImpl implements RecipeCommandService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeCommandServiceImpl.class);
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeConverter recipeConverter;
    private final RestaurantConverter restaurantConverter;
    private final UserQueryService userService;

    @Autowired
    public RecipeCommandServiceImpl(RecipeRepository recipeRepository,
                                    IngredientRepository ingredientRepository,
                                    RecipeConverter recipeConverter,
                                    RestaurantConverter restaurantConverter,
                                    UserQueryService userService) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeConverter = recipeConverter;
        this.restaurantConverter = restaurantConverter;
        this.userService = userService;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(value = {SQLException.class})
    public RecipeDto create(RecipeCreateDto recipeDto) {
        LOGGER.info("Create recipe {}", recipeDto.getName());
        UserDto userDto = userService.getCurrentUser();
        RestaurantDto restaurantDto = userDto.getRestaurant();
        Restaurant restaurant = restaurantConverter.dtoToEntity(restaurantDto);
        Recipe recipe = recipeConverter.dtoToEntity(recipeDto);
        recipe.setRestaurant(restaurant);
        if (recipeExists(recipe)) {
            LOGGER.error("Not unique name {}", recipe.getName());
            throw new ServiceException("Name should be unique");
        }
        if (ingredientsRepeatInRecipe(recipe)) {
            throw new ServiceException("Ingredients in recipe should be unique");
        }
        List<Ingredient> ingredients = recipe.getRecipeIngredients()
                .stream()
                .map(RecipeIngredient::getIngredient)
                .map((ingredient) ->
                        ingredientRepository.findByNameAndRestaurantId(ingredient.getName(), restaurant.getId())
                                .orElse(Ingredient.builder()
                                        .name(ingredient.getName())
                                        .restaurant(restaurant)
                                        .amount(BigDecimal.valueOf(0))
                                        .price(BigDecimal.valueOf(0))
                                        .measureUnit("g")
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

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(value = {SQLException.class})
    public RecipeDto update(RecipeUpdateDto recipeDto) {
        LOGGER.info("Update recipe {}", recipeDto.getName());
        UserDto userDto = userService.getCurrentUser();
        RestaurantDto restaurantDto = userDto.getRestaurant();
        Restaurant restaurant = restaurantConverter.dtoToEntity(restaurantDto);
        Recipe recipe = recipeConverter.dtoToEntity(recipeDto);
        recipe.setRestaurant(restaurant);
        Recipe persistRecipe = recipeRepository.findByNameAndRestaurantId(recipe.getName(), recipe.getRestaurant().getId())
                .orElseThrow(() -> new NoItemException("No such recipe"));
        if (ingredientsRepeatInRecipe(recipe)) {
            throw new ServiceException("Ingredients in recipe should be unique");
        }
        if (checkForNullAndZeroAmounts(recipe)) {
            throw new ServiceException("Amount can't be zero");
        }
        if (recipe.getRecipeIngredients().size() != 0) {
            List<Ingredient> ingredients = recipe.getRecipeIngredients()
                    .stream()
                    .map(RecipeIngredient::getIngredient)
                    .map((ingredient) -> ingredientRepository.findByNameAndRestaurantId(ingredient.getName(), restaurant.getId())
                            .orElse(Ingredient.builder()
                                    .name(ingredient.getName())
                                    .restaurant(restaurant)
                                    .amount(BigDecimal.valueOf(0))
                                    .price(BigDecimal.valueOf(0))
                                    .measureUnit("g")
                                    .build()))
                    .collect(Collectors.toList());
            for (int i = 0; i < ingredients.size(); i++) {
                recipe.getRecipeIngredients().get(i)
                        .setAmount(recipe.getRecipeIngredients().get(i).getAmount());
                recipe.getRecipeIngredients()
                        .get(i)
                        .setIngredient(ingredients.get(i));
            }
            persistRecipe.setRecipeIngredients(recipe.getRecipeIngredients());
        }
        if (recipe.getMargin() != null) {
            persistRecipe.setMargin(recipe.getMargin());
        }
        persistRecipe = recipeRepository.save(persistRecipe);
        return recipeConverter.entityToDto(persistRecipe);
    }

    @Override
    public boolean ingredientsRepeatInRecipe(Recipe recipe) {
        List<Ingredient> ingredients = recipe.getRecipeIngredients().stream().map(RecipeIngredient::getIngredient).collect(Collectors.toList());
        return !ingredients.stream().allMatch(new HashSet<>()::add);
    }

    @Override
    public boolean checkForNullAndZeroAmounts(Recipe recipe) {
        List<BigDecimal> amounts = recipe.getRecipeIngredients().stream().map(RecipeIngredient::getAmount).collect(Collectors.toList());
        return amounts.stream().anyMatch(amount -> (amount == null) || (amount.compareTo(BigDecimal.ZERO) <= 0));
    }

    @Override
    public boolean recipeExists(Recipe recipe) {
        LOGGER.info("Check for existing recipe {}", recipe.getName());
        return recipeRepository.countAllByNameAndRestaurantId(recipe.getName(), recipe.getRestaurant()
                .getId()) != 0;
    }
}
