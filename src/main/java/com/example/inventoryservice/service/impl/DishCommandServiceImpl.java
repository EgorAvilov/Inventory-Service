package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.DishConverter;
import com.example.inventoryservice.converter.RestaurantConverter;
import com.example.inventoryservice.dto.DishCreateDto;
import com.example.inventoryservice.dto.DishDto;
import com.example.inventoryservice.dto.RestaurantDto;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.Dish;
import com.example.inventoryservice.entity.Recipe;
import com.example.inventoryservice.entity.RecipeIngredient;
import com.example.inventoryservice.entity.Restaurant;
import com.example.inventoryservice.exception.NoItemException;
import com.example.inventoryservice.exception.ServiceException;
import com.example.inventoryservice.repository.DishRepository;
import com.example.inventoryservice.repository.RecipeIngredientRepository;
import com.example.inventoryservice.repository.RecipeRepository;
import com.example.inventoryservice.service.DishCommandService;
import com.example.inventoryservice.service.UserQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Service
public class DishCommandServiceImpl implements DishCommandService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DishCommandServiceImpl.class);
    private final DishRepository dishRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeRepository recipeRepository;
    private final DishConverter dishConverter;
    private final RestaurantConverter restaurantConverter;
    private final UserQueryService userService;

    @Autowired
    public DishCommandServiceImpl(DishRepository dishRepository,
                                  RecipeIngredientRepository recipeIngredientRepository,
                                  RecipeRepository recipeRepository,
                                  DishConverter dishConverter,
                                  RestaurantConverter restaurantConverter,
                                  UserQueryService userService) {
        this.dishRepository = dishRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeRepository = recipeRepository;
        this.dishConverter = dishConverter;
        this.restaurantConverter = restaurantConverter;
        this.userService = userService;
    }


    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(value = {SQLException.class})
    public DishDto create(DishCreateDto dishDto) {
        LOGGER.info("Create dish");
        UserDto userDto = userService.getCurrentUser();
        RestaurantDto restaurantDto = userDto.getRestaurant();
        Restaurant restaurant = restaurantConverter.dtoToEntity(restaurantDto);
        Dish dish = dishConverter.dtoToEntity(dishDto);
        dish.setRestaurant(restaurant);
        if (!recipeExists(dish)) {
            LOGGER.error("No such recipe {}", dish.getRecipe()
                    .getName());
            throw new NoItemException("No such recipe");
        }
        cookingDish(dish);
        calculatePrice(dish);
        Dish persistDish = dishRepository.save(dish);
        return dishConverter.entityToDto(persistDish);
    }

    public void calculatePrice(Dish dish) {
        LOGGER.info("Calculating price of dish {}", dish.getRecipe()
                .getName());
        Recipe recipe = recipeRepository.findByName(dish.getRecipe()
                .getName()).orElseThrow(() -> new NoItemException("No such recipe"));
        BigDecimal percent = recipe.getPercent();
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        List<Long> recipeIngredientIds = recipe.getRecipeIngredients()
                .stream()
                .map(RecipeIngredient::getId)
                .collect(Collectors.toList());
        List<RecipeIngredient> requiredRecipeIngredients = recipeIngredientRepository.findAllByIdIn(recipeIngredientIds);
        for (RecipeIngredient requiredRecipeIngredient : requiredRecipeIngredients) {
            totalPrice = totalPrice.add(requiredRecipeIngredient.getAmount().multiply(requiredRecipeIngredient.getIngredient().getPrice()));
        }
        dish.setPrice(totalPrice.multiply(percent).setScale(2, RoundingMode.HALF_UP));
    }

    @Override
    public void cookingDish(Dish dish) {
        LOGGER.info("Cooking dish {}", dish.getRecipe()
                .getName());
        Recipe recipe = recipeRepository.findByName(dish.getRecipe()
                .getName()).orElseThrow();
        dish.setRecipe(recipe);
        List<Long> recipeIngredientIds = recipe.getRecipeIngredients()
                .stream()
                .map(RecipeIngredient::getId)
                .collect(Collectors.toList());
        List<String> notEnoughIngredientsList = new ArrayList<>();
        List<RecipeIngredient> requiredRecipeIngredients = recipeIngredientRepository.findAllByIdIn(recipeIngredientIds);
        for (RecipeIngredient requiredRecipeIngredient : requiredRecipeIngredients) {
            if (requiredRecipeIngredient.getAmount()
                    .compareTo(requiredRecipeIngredient.getIngredient()
                            .getAmount()) > 0) {
                notEnoughIngredientsList.add(requiredRecipeIngredient.getIngredient()
                        .getName());
            }
            BigDecimal existingAmount = requiredRecipeIngredient.getIngredient()
                    .getAmount();
            requiredRecipeIngredient.getIngredient()
                    .setAmount(existingAmount.subtract(requiredRecipeIngredient.getAmount()));
        }
        if (notEnoughIngredientsList.size() != 0) {
            LOGGER.error("Not enough ingredients {}", notEnoughIngredientsList.toString());
            throw new ServiceException("Not enough ingredients: " + notEnoughIngredientsList.toString());
        }
        recipeIngredientRepository.saveAll(requiredRecipeIngredients);
    }

    public boolean recipeExists(Dish dish) {
        LOGGER.info("Check for existing recipe {}", dish.getRecipe()
                .getName());
        return recipeRepository.countAllByNameAndRestaurantId(dish.getRecipe()
                        .getName(),
                dish.getRestaurant()
                        .getId()) != 0;
    }
}
