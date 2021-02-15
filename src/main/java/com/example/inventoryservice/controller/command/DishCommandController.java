package com.example.inventoryservice.controller.command;

import com.example.inventoryservice.dto.DishDto;
import com.example.inventoryservice.service.DishCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/dishes")
public class DishCommandController {
    private final DishCommandService dishService;

    @Autowired
    public DishCommandController(DishCommandService dishService) {
        this.dishService = dishService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('KITCHEN_STUFF')")
    public ResponseEntity create(@RequestBody @Valid DishDto dishDto) {
        return new ResponseEntity<>(dishService.create(dishDto), HttpStatus.CREATED);
    }
}
