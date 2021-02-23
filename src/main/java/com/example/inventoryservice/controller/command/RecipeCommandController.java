package com.example.inventoryservice.controller.command;

import com.example.inventoryservice.dto.RecipeCreateDto;
import com.example.inventoryservice.dto.RecipeUpdateDto;
import com.example.inventoryservice.service.RecipeCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/recipes")
@PreAuthorize("hasAuthority('KITCHEN_CHEF')")
public class RecipeCommandController {
    private final RecipeCommandService recipeService;

    @Autowired
    public RecipeCommandController(RecipeCommandService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid RecipeCreateDto recipeDto) {
        return new ResponseEntity<>(recipeService.create(recipeDto), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity update(@RequestBody @Valid RecipeUpdateDto recipeDto) {
        return new ResponseEntity<>(recipeService.update(recipeDto), HttpStatus.OK);
    }
}
