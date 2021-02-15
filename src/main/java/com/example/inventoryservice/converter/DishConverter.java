package com.example.inventoryservice.converter;

import com.example.inventoryservice.dto.DishDto;
import com.example.inventoryservice.entity.Dish;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DishConverter {
    public DishDto entityToDto(Dish dish) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dish, DishDto.class);
    }

    public Dish dtoToEntity(DishDto dishDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dishDto, Dish.class);
    }

    public List<DishDto> entityToDto(List<Dish> dishes) {
        return dishes.stream()
                     .map(this::entityToDto)
                     .collect(Collectors.toList());
    }

    public List<Dish> dtoToEntity(List<DishDto> dishDtos) {
        return dishDtos.stream()
                       .map(this::dtoToEntity)
                       .collect(Collectors.toList());
    }
}
