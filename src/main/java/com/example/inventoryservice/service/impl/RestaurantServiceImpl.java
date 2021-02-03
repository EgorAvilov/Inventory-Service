package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.converter.RestaurantConverter;
import com.example.inventoryservice.dto.RestaurantDto;
import com.example.inventoryservice.entity.Restaurant;
import com.example.inventoryservice.exception.ServiceException;
import com.example.inventoryservice.repository.RestaurantRepository;
import com.example.inventoryservice.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantConverter restaurantConverter;
    Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantConverter restaurantConverter) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantConverter = restaurantConverter;
    }

    @Override
    public RestaurantDto create(RestaurantDto restaurantDto) {
        logger.info("Create restaurant");
        if (restaurantExists(restaurantDto.getName())) {
            logger.error("Not unique name {}", restaurantDto.getName());
            throw new ServiceException("Name should be unique");
        }
        Restaurant restaurant = restaurantConverter.dtoToEntity(restaurantDto);
        Restaurant persistRestaurant = restaurantRepository.save(restaurant);
        return restaurantConverter.entityToDto(persistRestaurant);
    }

    @Override
    public boolean restaurantExists(String name) {
        logger.info("Check for existing restaurant {}", name);
        return restaurantRepository.findAllByNameIgnoreCase(name)
                                   .size() != 0;
    }
}
