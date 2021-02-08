package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.DishDto;
import com.example.inventoryservice.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/dishes")
public class DishController {
    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public ResponseEntity findAllByRestaurant() {
        return new ResponseEntity<>(dishService.findAllByRestaurant(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid DishDto dishDto) {
        return new ResponseEntity<>(dishService.create(dishDto), HttpStatus.CREATED);
    }
}
