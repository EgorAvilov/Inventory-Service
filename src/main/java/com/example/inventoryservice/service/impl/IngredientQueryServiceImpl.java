package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.IngredientConverter;
import com.example.inventoryservice.dto.IngredientDto;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.repository.IngredientRepository;
import com.example.inventoryservice.service.IngredientQueryService;
import com.example.inventoryservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientQueryServiceImpl implements IngredientQueryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientQueryServiceImpl.class);
    private final IngredientRepository ingredientRepository;
    private final IngredientConverter ingredientConverter;
    private final UserService userService;

    @Autowired
    public IngredientQueryServiceImpl(IngredientRepository ingredientRepository,
                                      IngredientConverter ingredientConverter,
                                      UserService userService) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientConverter = ingredientConverter;
        this.userService = userService;
    }

    @Override
    public List<IngredientDto> findAllByRestaurant() {
        LOGGER.info("Find all ingredients by restaurant");
        UserDto userDto = userService.getCurrentUser();
        List<Ingredient> ingredients = ingredientRepository.findAllByRestaurantId(userDto.getRestaurant()
                                                                                         .getId());
        return ingredientConverter.entityToDto(ingredients);
    }
}
