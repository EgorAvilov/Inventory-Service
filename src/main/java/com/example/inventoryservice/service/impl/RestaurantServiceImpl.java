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
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantServiceImpl.class);
    private final RestaurantRepository restaurantRepository;
    private final RestaurantConverter restaurantConverter;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantConverter restaurantConverter) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantConverter = restaurantConverter;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Retryable(value = {SQLException.class})
    public RestaurantDto create(RestaurantDto restaurantDto) {
        LOGGER.info("Create restaurant");
        if (restaurantExists(restaurantDto.getName())) {
            LOGGER.error("Not unique name {}", restaurantDto.getName());
            throw new ServiceException("Name should be unique");
        }
        Restaurant restaurant = restaurantConverter.dtoToEntity(restaurantDto);
        Restaurant persistRestaurant = restaurantRepository.save(restaurant);
        return restaurantConverter.entityToDto(persistRestaurant);
    }

    @Override
    public boolean restaurantExists(String name) {
        LOGGER.info("Check for existing restaurant {}", name);
        return restaurantRepository.findAllByName(name) != 0;
    }
}
