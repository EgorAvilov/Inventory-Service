package com.example.inventoryservice.controller.query;

import com.example.inventoryservice.service.IngredientQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/ingredients")
public class IngredientQueryController {
    private final IngredientQueryService ingredientService;

    @Autowired
    public IngredientQueryController(IngredientQueryService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('INVENTORY_MANAGER')")
    public ResponseEntity findAllByRestaurant() {
        return new ResponseEntity<>(ingredientService.findAllByRestaurant(), HttpStatus.OK);
    }
}
