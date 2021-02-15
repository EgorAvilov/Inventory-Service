package com.example.inventoryservice.controller.query;

import com.example.inventoryservice.service.RecipeQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/recipes")
@PreAuthorize("hasAuthority('KITCHEN_CHEF')")
public class RecipeQueryController {
    private final RecipeQueryService recipeService;

    @Autowired
    public RecipeQueryController(RecipeQueryService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity findAllByRestaurant() {
        return new ResponseEntity<>(recipeService.findAllByRestaurant(), HttpStatus.OK);
    }
}
