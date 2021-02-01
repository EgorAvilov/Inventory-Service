package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.DishConverter;
import com.example.inventoryservice.dto.DishDto;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.Dish;
import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.entity.Recipe;
import com.example.inventoryservice.entity.RecipeIngredient;
import com.example.inventoryservice.exception.ServiceException;
import com.example.inventoryservice.repository.DishRepository;
import com.example.inventoryservice.repository.IngredientRepository;
import com.example.inventoryservice.repository.RecipeIngredientRepository;
import com.example.inventoryservice.repository.RecipeRepository;
import com.example.inventoryservice.service.DishService;
import com.example.inventoryservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeRepository recipeRepository;
    private final DishConverter dishConverter;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public DishServiceImpl(DishRepository dishRepository, IngredientRepository ingredientRepository, RecipeIngredientRepository recipeIngredientRepository, RecipeRepository recipeRepository, DishConverter dishConverter, UserService userService) {
        this.dishRepository = dishRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeRepository = recipeRepository;
        this.dishConverter = dishConverter;
        this.userService = userService;
    }

    @Override
    public DishDto create(DishDto dishDto) {
        logger.info("Create dish");
        Dish dish = dishConverter.dtoToEntity(dishDto);
        checkForEnoughIngredients(dish);
        Dish persistDish = dishRepository.save(dish);
        return dishConverter.entityToDto(persistDish);
    }

    @Override
    public List<DishDto> findAllByRestaurant() {
        logger.info("Find all dishes by restaurant");
        UserDto userDto = userService.getCurrentUser();
        List<Dish> ingredients = dishRepository.findAllByRestaurant_Id(userDto.getRestaurant()
                                                                              .getId());
        return dishConverter.entityToDto(ingredients);
    }
    @Override
    public void checkForEnoughIngredients(Dish dish) {
        Recipe recipe = recipeRepository.getOne(dish.getRecipe()
                                                    .getId());
        List<Long> ingredientIds = recipe.getRecipeIngredients()
                                         .stream()
                                         .map(RecipeIngredient::getIngredient)
                                         .map(Ingredient::getId)
                                         .collect(Collectors.toList());
        List<RecipeIngredient> requiredRecipeIngredients = recipeIngredientRepository.findAllById(ingredientIds);
        List<Ingredient> existingIngredients = ingredientRepository.findAllById(ingredientIds);
        if (requiredRecipeIngredients.size() > existingIngredients.size()) {
            throw new ServiceException("Not enough ingredients");
        }
        for (RecipeIngredient requiredRecipeIngredient : requiredRecipeIngredients) {
            for (Ingredient existingIngredient : existingIngredients) {
                if (requiredRecipeIngredient.getIngredient().getName()
                                            .equalsIgnoreCase(existingIngredient.getName())) {
                    if (existingIngredient.getAmount()
                                          .compareTo(requiredRecipeIngredient.getAmount()) < 0) {
                        throw new ServiceException("Not enough ingredients");
                    }
                    BigDecimal existingAmount = existingIngredient.getAmount();
                    existingIngredient.setAmount(existingAmount.subtract(requiredRecipeIngredient.getAmount()));
                }
            }
            ingredientRepository.saveAll(existingIngredients);
        }
    }
}
