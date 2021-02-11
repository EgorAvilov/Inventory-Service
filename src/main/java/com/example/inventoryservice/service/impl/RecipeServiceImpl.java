package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.RecipeConverter;
import com.example.inventoryservice.dto.RecipeDto;
import com.example.inventoryservice.dto.RestaurantDto;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.Recipe;
import com.example.inventoryservice.exception.ServiceException;
import com.example.inventoryservice.repository.RecipeRepository;
import com.example.inventoryservice.service.RecipeService;
import com.example.inventoryservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final RecipeRepository recipeRepository;
    private final RecipeConverter recipeConverter;
    private final UserService userService;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeConverter recipeConverter,
                             UserService userService) {
        this.recipeRepository = recipeRepository;
        this.recipeConverter = recipeConverter;
        this.userService = userService;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Retryable(value = {SQLException.class})
    public RecipeDto create(RecipeDto recipeDto) {
        LOGGER.info("Create recipe");
        UserDto userDto = userService.getCurrentUser();
        RestaurantDto restaurantDto = userDto.getRestaurant();
        recipeDto.setRestaurant(restaurantDto);
        Recipe recipe = recipeConverter.dtoToEntity(recipeDto);
        if (recipeExists(recipe)) {
            LOGGER.error("Not unique name {}", recipe.getName());
            throw new ServiceException("Name should be unique");
        }

        Recipe persistRecipe = recipeRepository.save(recipe);
        return recipeConverter.entityToDto(persistRecipe);
    }

    @Override
    public List<RecipeDto> findAllByRestaurant() {
        LOGGER.info("Find all recipes by restaurant");
        UserDto userDto = userService.getCurrentUser();
        List<Recipe> recipes = recipeRepository.findAllByRestaurantId(userDto.getRestaurant()
                                                                             .getId());
        return recipeConverter.entityToDto(recipes);
    }

    @Override
    public boolean recipeExists(Recipe recipe) {
        LOGGER.info("Check for existing recipe {}", recipe.getName());
        return recipeRepository.countAllByNameAndRestaurantId(recipe.getName(), recipe.getRestaurant()
                                                                                     .getId()) != 0;
    }
}
