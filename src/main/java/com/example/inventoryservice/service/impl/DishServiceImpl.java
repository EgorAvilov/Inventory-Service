package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.DishConverter;
import com.example.inventoryservice.dto.DishDto;
import com.example.inventoryservice.dto.UserDto;
import com.example.inventoryservice.entity.Dish;
import com.example.inventoryservice.repository.DishRepository;
import com.example.inventoryservice.service.DishService;
import com.example.inventoryservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final DishConverter dishConverter;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public DishServiceImpl(DishRepository dishRepository, DishConverter dishConverter, UserService userService) {
        this.dishRepository = dishRepository;
        this.dishConverter = dishConverter;
        this.userService = userService;
    }

    @Override
    public DishDto create(DishDto dishDto) {
        logger.info("Create dish");
        Dish dish = dishConverter.dtoToEntity(dishDto);
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
}
