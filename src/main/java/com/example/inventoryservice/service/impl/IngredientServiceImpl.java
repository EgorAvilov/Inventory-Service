package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.IngredientConverter;
import com.example.inventoryservice.dto.IngredientDto;
import com.example.inventoryservice.dto.RestaurantDto;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.exception.NoItemException;
import com.example.inventoryservice.exception.ServiceException;
import com.example.inventoryservice.repository.IngredientRepository;
import com.example.inventoryservice.service.IngredientService;
import com.example.inventoryservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final IngredientRepository ingredientRepository;
    private final IngredientConverter ingredientConverter;
    private final UserService userService;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientConverter ingredientConverter, UserService userService) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientConverter = ingredientConverter;
        this.userService = userService;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Retryable(value = {SQLException.class})
    public IngredientDto create(IngredientDto ingredientDto) {
        LOGGER.info("Create ingredient");
        UserDto userDto = userService.getCurrentUser();
        RestaurantDto restaurantDto = userDto.getRestaurant();
        ingredientDto.setRestaurant(restaurantDto);
        Ingredient ingredient = ingredientConverter.dtoToEntity(ingredientDto);
        if (ingredientExists(ingredient)) {
            throw new ServiceException("Not unique ingredient");
        }
        Ingredient persistIngredient = ingredientRepository.save(ingredient);
        return ingredientConverter.entityToDto(persistIngredient);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Retryable(value = {SQLException.class})
    public IngredientDto updateAmount(IngredientDto ingredientDto) {
        LOGGER.info("Update ingredient amount");
        Ingredient ingredient = ingredientConverter.dtoToEntity(ingredientDto);
        Ingredient persistIngredient = ingredientRepository.findById(ingredient.getId())
                                                           .orElseThrow(() -> new NoItemException("No such ingredient"));
        BigDecimal persistAmount = persistIngredient.getAmount();
        persistIngredient.setAmount(persistAmount.add(ingredient.getAmount()));
        persistIngredient = ingredientRepository.save(persistIngredient);
        return ingredientConverter.entityToDto(persistIngredient);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Retryable(value = {SQLException.class})
    public IngredientDto updatePrice(IngredientDto ingredientDto) {
        LOGGER.info("Update ingredient price");
        Ingredient ingredient = ingredientConverter.dtoToEntity(ingredientDto);
        Ingredient persistIngredient = ingredientRepository.findById(ingredient.getId())
                                                           .orElseThrow(() -> new NoItemException("No such ingredient"));
        BigDecimal persistPrice = persistIngredient.getPrice();
        persistIngredient.setPrice(persistPrice.add(ingredient.getAmount()));
        persistIngredient = ingredientRepository.save(persistIngredient);
        return ingredientConverter.entityToDto(persistIngredient);
    }

    @Override
    public List<IngredientDto> findAllByRestaurant() {
        LOGGER.info("Find all ingredients by restaurant");
        UserDto userDto = userService.getCurrentUser();
        List<Ingredient> ingredients = ingredientRepository.findAllByRestaurantId(userDto.getRestaurant()
                                                                                         .getId());
        return ingredientConverter.entityToDto(ingredients);
    }

    @Override
    public boolean ingredientExists(Ingredient ingredient) {
        return ingredientRepository.findAllByNameAndRestaurantId(ingredient.getName(), ingredient.getRestaurant()
                                                                                                 .getId()) != 0;
    }
}
