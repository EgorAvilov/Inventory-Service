package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.DishConverter;
import com.example.inventoryservice.dto.DishDto;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.Dish;
import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.exception.ServiceException;
import com.example.inventoryservice.repository.DishRepository;
import com.example.inventoryservice.repository.IngredientRepository;
import com.example.inventoryservice.service.DishService;
import com.example.inventoryservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final IngredientRepository ingredientRepository;
    private final DishConverter dishConverter;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public DishServiceImpl(DishRepository dishRepository, IngredientRepository ingredientRepository, DishConverter dishConverter, UserService userService) {
        this.dishRepository = dishRepository;
        this.ingredientRepository = ingredientRepository;
        this.dishConverter = dishConverter;
        this.userService = userService;
    }

    @Override
    public DishDto create(DishDto dishDto) {
        logger.info("Create dish");
        Dish dish = dishConverter.dtoToEntity(dishDto);
    //    ingredientsExist(dish);
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
   /* @Transactional
    public void ingredientsExist(Dish dish) {
        List<Ingredient> requestedIngredients = dish.getRecipe()
                                           .getIngredients();
        List<String> ingredientNames = requestedIngredients.stream()
                                                  .map(Ingredient::getName)
                                                  .collect(Collectors.toList());
        List<Ingredient> existingIngredients = ingredientRepository.findAllByRestaurant_IdAndNameIn(dish.getRestaurant()//случайный порядок
                                                                                                                .getId(), ingredientNames);
        if(requestedIngredients.size()!=existingIngredients.size()){
            throw new ServiceException("Not enough ingredients");
        }
        for(Ingredient existingIngredient:existingIngredients){
            for(Ingredient requestedIngredient:requestedIngredients){
                if(existingIngredient.equals(requestedIngredient)){
                    if(existingIngredient.getAmount().compareTo(requestedIngredient.getAmount())<0){
                        throw new ServiceException("Not enough ingredients");
                    }
                    BigDecimal existingAmount=existingIngredient.getAmount();
                    existingIngredient.setAmount(existingAmount.subtract(requestedIngredient.getAmount()));
                }
            }
        }
        ingredientRepository.saveAll(existingIngredients);
    }*/
}
