package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.dto.DishDto;
import com.example.inventoryservice.service.DishService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Override
    public DishDto create(DishDto dishDto) {
        return null;
    }

    @Override
    public List<DishDto> findAllByRestaurant() {
        return null;
    }
}
