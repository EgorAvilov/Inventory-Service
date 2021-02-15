package com.example.inventoryservice.controller.command;

import com.example.inventoryservice.dto.RestaurantDto;
import com.example.inventoryservice.service.RestaurantCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/restaurants")
public class RestaurantCommandController {
    private final RestaurantCommandService restaurantService;

    @Autowired
    public RestaurantCommandController(RestaurantCommandService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid RestaurantDto restaurantDto) {
        return new ResponseEntity<>(restaurantService.create(restaurantDto), HttpStatus.CREATED);
    }
}
