package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.RecipeDto;
import com.example.inventoryservice.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('KITCHEN_CHEF')")
    public ResponseEntity findAllByRestaurant() {
        return new ResponseEntity<>(recipeService.findAllByRestaurant(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('KITCHEN_CHEF')")
    public ResponseEntity create(@RequestBody @Valid RecipeDto recipeDto) {
        return new ResponseEntity<>(recipeService.create(recipeDto), HttpStatus.CREATED);
    }
}
