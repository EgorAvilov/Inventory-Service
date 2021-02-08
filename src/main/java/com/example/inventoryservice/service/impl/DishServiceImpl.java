package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.DishConverter;
import com.example.inventoryservice.dto.DishDto;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.Dish;
import com.example.inventoryservice.entity.Recipe;
import com.example.inventoryservice.entity.RecipeIngredient;
import com.example.inventoryservice.exception.NoItemException;
import com.example.inventoryservice.exception.ServiceException;
import com.example.inventoryservice.repository.DishRepository;
import com.example.inventoryservice.repository.RecipeIngredientRepository;
import com.example.inventoryservice.repository.RecipeRepository;
import com.example.inventoryservice.service.DishService;
import com.example.inventoryservice.service.UserService;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeRepository recipeRepository;
    private final DishConverter dishConverter;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public DishServiceImpl(DishRepository dishRepository,
                           RecipeIngredientRepository recipeIngredientRepository,
                           RecipeRepository recipeRepository,
                           DishConverter dishConverter,
                           UserService userService) {
        this.dishRepository = dishRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeRepository = recipeRepository;
        this.dishConverter = dishConverter;
        this.userService = userService;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Retryable(value = {SQLException.class})
    public DishDto create(DishDto dishDto) {
        logger.info("Create dish");
        Dish dish = dishConverter.dtoToEntity(dishDto);
        if (!recipeExists(dish)) {
            logger.error("No such recipe {}", dish.getRecipe()
                                                  .getName());
            throw new NoItemException("No such recipe");
        }
        cookingDish(dish);
        Dish persistDish = dishRepository.save(dish);
        return dishConverter.entityToDto(persistDish);
    }

    @Override
    public List<DishDto> findAllByRestaurant() {
        logger.info("Find all dishes by restaurant");
        UserDto userDto = userService.getCurrentUser();
        List<Dish> ingredients = dishRepository.findAllByRestaurantId(userDto.getRestaurant()
                                                                             .getId());
        return dishConverter.entityToDto(ingredients);
    }

    @Override
    public void cookingDish(Dish dish) {
        Recipe recipe = recipeRepository.getOne(dish.getRecipe()
                                                    .getId());
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
            throw new ServiceException("Not enough ingredients: " + notEnoughIngredientsList.toString());
        }
        recipeIngredientRepository.saveAll(requiredRecipeIngredients);
    }

    public boolean recipeExists(Dish dish) {
        return recipeRepository.findAllByNameAndRestaurantId(dish.getRecipe()
                                                                 .getName(),
                dish.getRestaurant()
                    .getId()) != 0;
    }
}
