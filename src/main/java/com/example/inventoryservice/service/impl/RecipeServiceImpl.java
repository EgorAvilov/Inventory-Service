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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeConverter recipeConverter;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeConverter recipeConverter, UserService userService) {
        this.recipeRepository = recipeRepository;
        this.recipeConverter = recipeConverter;
        this.userService = userService;
    }

    @Override
    public RecipeDto create(RecipeDto recipeDto) {
        logger.info("Create recipe");
      /*  UserDto userDto = userService.getCurrentUser();
        RestaurantDto restaurantDto = userDto.getRestaurant();
        recipeDto.setRestaurant(restaurantDto);
        if (recipeExists(recipeDto.getName(), recipeDto.getRestaurant()
                                                       .getId())) {
            logger.error("Not unique name {}", recipeDto.getName());
            throw new ServiceException("Name should be unique");
        }*/
        //в validation сделать проверку что не пришли нули в количестве ингредиентов и не пустой лист ингредиентов
        Recipe recipe = recipeConverter.dtoToEntity(recipeDto);
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
    public boolean recipeExists(String name, Long restaurantId) {
        logger.info("Check for existing recipe {}", name);
        return recipeRepository.findAllByNameIgnoreCaseAndRestaurant_Id(name, restaurantId)
                               .size() != 0;
    }
}
