package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.IngredientDto;
import com.example.inventoryservice.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping()
    public ResponseEntity findAllByRestaurant() {
        return new ResponseEntity<>(ingredientService.findAllByRestaurant(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid IngredientDto ingredientDto) {
        return new ResponseEntity<>(ingredientService.create(ingredientDto), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/amount")
    public ResponseEntity updateAmount(@RequestBody @Valid IngredientDto ingredientDto) {
        return new ResponseEntity<>(ingredientService.updateAmount(ingredientDto), HttpStatus.OK);
    }

    @PatchMapping(value = "/price")
    public ResponseEntity updatePrice(@RequestBody @Valid IngredientDto ingredientDto) {
        return new ResponseEntity<>(ingredientService.updatePrice(ingredientDto), HttpStatus.OK);
    }
}
