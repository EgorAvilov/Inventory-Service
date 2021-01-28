package com.example.inventoryservice.converter;

import com.example.inventoryservice.dto.RestaurantDto;
import com.example.inventoryservice.entity.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantConverter {
    public RestaurantDto entityToDto(Restaurant restaurant) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(restaurant, RestaurantDto.class);
    }

    public Restaurant dtoToEntity(RestaurantDto restaurantDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(restaurantDto, Restaurant.class);
    }

    public List<RestaurantDto> entityToDto(List<Restaurant> restaurants) {
        return restaurants.stream()
                          .map(this::entityToDto)
                          .collect(Collectors.toList());
    }

    public List<Restaurant> dtoToEntity(List<RestaurantDto> restaurantDtos) {
        return restaurantDtos.stream()
                             .map(this::dtoToEntity)
                             .collect(Collectors.toList());
    }
}
