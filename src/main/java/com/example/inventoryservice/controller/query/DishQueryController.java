package com.example.inventoryservice.controller.query;

import com.example.inventoryservice.service.DishQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/dishes")
@PreAuthorize("hasAuthority('KITCHEN_STUFF')")
public class DishQueryController {
    private final DishQueryService dishService;

    @Autowired
    public DishQueryController(DishQueryService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public ResponseEntity findAllByRestaurant() {
        return new ResponseEntity<>(dishService.findAllByRestaurant(), HttpStatus.OK);
    }
}
