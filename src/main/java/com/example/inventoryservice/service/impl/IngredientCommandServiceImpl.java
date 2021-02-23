package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.IngredientConverter;
import com.example.inventoryservice.converter.RestaurantConverter;
import com.example.inventoryservice.dto.*;
import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.entity.Restaurant;
import com.example.inventoryservice.exception.NoItemException;
import com.example.inventoryservice.exception.ServiceException;
import com.example.inventoryservice.repository.IngredientRepository;
import com.example.inventoryservice.service.IngredientCommandService;
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

@Service
public class IngredientCommandServiceImpl implements IngredientCommandService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientCommandServiceImpl.class);
    private final IngredientRepository ingredientRepository;
    private final IngredientConverter ingredientConverter;
    private final RestaurantConverter restaurantConverter;
    private final UserQueryService userService;

    @Autowired
    public IngredientCommandServiceImpl(IngredientRepository ingredientRepository,
                                        IngredientConverter ingredientConverter,
                                        RestaurantConverter restaurantConverter,
                                        UserQueryService userService) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientConverter = ingredientConverter;
        this.restaurantConverter = restaurantConverter;
        this.userService = userService;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(value = {SQLException.class})
    public IngredientDto create(IngredientDto ingredientDto) {
        LOGGER.info("Create ingredient");
        UserDto userDto = userService.getCurrentUser();
        RestaurantDto restaurantDto = userDto.getRestaurant();
        Restaurant restaurant = restaurantConverter.dtoToEntity(restaurantDto);
        Ingredient ingredient = ingredientConverter.dtoToEntity(ingredientDto);
        ingredient.setRestaurant(restaurant);
        if (ingredientExists(ingredient)) {
            LOGGER.error("Not unique ingredient {}", ingredient.getName());
            throw new ServiceException("Not unique ingredient");
        }
        ingredient = ingredientRepository.save(ingredient);
        return ingredientConverter.entityToDto(ingredient);
    }

    @Override
    public IngredientDto updateAmount(IngredientUpdateAmountDto ingredientDto) {
        LOGGER.info("Update ingredient amount");
        Ingredient ingredient = ingredientConverter.dtoToEntity(ingredientDto);
        Ingredient persistIngredient = ingredientRepository.findByNameAndRestaurantId(ingredient.getName(),ingredient.getRestaurant().getId())
                .orElseThrow(() -> new NoItemException("No such ingredient"));
        BigDecimal persistAmount = persistIngredient.getAmount();
        persistIngredient.setAmount(persistAmount.add(ingredient.getAmount()));
        persistIngredient = ingredientRepository.save(persistIngredient);
        return ingredientConverter.entityToDto(persistIngredient);
    }

    @Override
    public IngredientDto updatePrice(IngredientUpdatePriceDto ingredientDto) {
        LOGGER.info("Update ingredient price");
        Ingredient ingredient = ingredientConverter.dtoToEntity(ingredientDto);
        Ingredient persistIngredient = ingredientRepository.findByNameAndRestaurantId(ingredient.getName(),ingredient.getRestaurant().getId())
                .orElseThrow(() -> new NoItemException("No such ingredient"));
        BigDecimal persistPrice = persistIngredient.getPrice();
        persistIngredient.setPrice(persistPrice.add(ingredient.getPrice()));
        persistIngredient = ingredientRepository.save(persistIngredient);
        return ingredientConverter.entityToDto(persistIngredient);
    }

    @Override
    public IngredientDto update(IngredientUpdateDto ingredientDto) {
        LOGGER.info("Update ingredient");
        Ingredient ingredient = ingredientConverter.dtoToEntity(ingredientDto);
        Ingredient persistIngredient = ingredientRepository.findByNameAndRestaurantId(ingredient.getName(),ingredient.getRestaurant().getId())
                .orElseThrow(() -> new NoItemException("No such ingredient"));
        BigDecimal weightedPrice = weightPrice(ingredient, persistIngredient);
        BigDecimal totalAmount = persistIngredient.getAmount().add(ingredient.getAmount());
        persistIngredient.setAmount(totalAmount);
        persistIngredient.setPrice(weightedPrice);
        persistIngredient = ingredientRepository.save(persistIngredient);
        return ingredientConverter.entityToDto(persistIngredient);
    }

    public boolean ingredientExists(Ingredient ingredient) {
        LOGGER.info("Check for existing ingredient {}", ingredient.getName());
        return ingredientRepository.countAllByNameAndRestaurantId(ingredient.getName(), ingredient.getRestaurant()
                .getId()) != 0;
    }

    public BigDecimal weightPrice(Ingredient ingredient, Ingredient persistIngredient) {
        BigDecimal totalOldPrice = persistIngredient.getAmount().multiply(persistIngredient.getPrice());
        BigDecimal totalNewPrice = ingredient.getAmount().multiply(ingredient.getPrice());
        BigDecimal totalPrice = totalOldPrice.add(totalNewPrice);
        BigDecimal totalAmount = persistIngredient.getAmount().add(ingredient.getAmount());
        return totalPrice.divide(totalAmount, 2, RoundingMode.HALF_EVEN);
    }
}
