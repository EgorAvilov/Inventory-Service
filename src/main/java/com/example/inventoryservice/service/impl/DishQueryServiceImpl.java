package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.DishConverter;
import com.example.inventoryservice.dto.DishDto;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.Dish;
import com.example.inventoryservice.repository.DishRepository;
import com.example.inventoryservice.service.DishQueryService;
import com.example.inventoryservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishQueryServiceImpl implements DishQueryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DishQueryServiceImpl.class);
    private final DishRepository dishRepository;
    private final DishConverter dishConverter;
    private final UserService userService;

    @Autowired
    public DishQueryServiceImpl(DishRepository dishRepository, DishConverter dishConverter, UserService userService) {
        this.dishRepository = dishRepository;
        this.dishConverter = dishConverter;
        this.userService = userService;
    }


    @Override
    public List<DishDto> findAllByRestaurant() {
        LOGGER.info("Find all dishes by restaurant");
        UserDto userDto = userService.getCurrentUser();
        List<Dish> ingredients = dishRepository.findAllByRestaurantId(userDto.getRestaurant()
                                                                             .getId());
        return dishConverter.entityToDto(ingredients);
    }
}
