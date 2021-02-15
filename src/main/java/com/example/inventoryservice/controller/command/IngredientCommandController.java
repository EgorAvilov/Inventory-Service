package com.example.inventoryservice.controller.command;

import com.example.inventoryservice.dto.IngredientDto;
import com.example.inventoryservice.dto.IngredientUpdateAmountDto;
import com.example.inventoryservice.dto.IngredientUpdatePriceDto;
import com.example.inventoryservice.service.IngredientCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/ingredients")
public class IngredientCommandController {
    private final IngredientCommandService ingredientService;

    @Autowired
    public IngredientCommandController(IngredientCommandService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('INVENTORY_MANAGER')")
    public ResponseEntity create(@RequestBody @Valid IngredientDto ingredientDto) {
        return new ResponseEntity<>(ingredientService.create(ingredientDto), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/amount")
    @PreAuthorize("hasAuthority('INVENTORY_MANAGER')")
    public ResponseEntity updateAmount(@RequestBody @Valid IngredientUpdateAmountDto ingredientDto) {
        return new ResponseEntity<>(ingredientService.updateAmount(ingredientDto), HttpStatus.OK);
    }

    @PatchMapping(value = "/price")
    @PreAuthorize("hasAuthority('INVENTORY_MANAGER')")
    public ResponseEntity updatePrice(@RequestBody @Valid IngredientUpdatePriceDto ingredientDto) {
        return new ResponseEntity<>(ingredientService.updatePrice(ingredientDto), HttpStatus.OK);
    }
}
