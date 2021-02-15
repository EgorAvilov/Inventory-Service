package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.RecipeConverter;
import com.example.inventoryservice.dto.RecipeDto;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.Recipe;
import com.example.inventoryservice.repository.RecipeRepository;
import com.example.inventoryservice.service.RecipeQueryService;
import com.example.inventoryservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeQueryServiceImpl implements RecipeQueryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeQueryServiceImpl.class);
    private final RecipeRepository recipeRepository;
    private final RecipeConverter recipeConverter;
    private final UserService userService;

    @Autowired
    public RecipeQueryServiceImpl(RecipeRepository recipeRepository,
                                  RecipeConverter recipeConverter,
                                  UserService userService) {
        this.recipeRepository = recipeRepository;
        this.recipeConverter = recipeConverter;
        this.userService = userService;
    }

    @Override
    public List<RecipeDto> findAllByRestaurant() {
        LOGGER.info("Find all recipes by restaurant");
        UserDto userDto = userService.getCurrentUser();
        List<Recipe> recipes = recipeRepository.findAllByRestaurantId(userDto.getRestaurant()
                                                                             .getId());
        return recipeConverter.entityToDto(recipes);
    }
}
