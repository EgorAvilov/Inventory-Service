package com.example.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeUpdateDto {

    private String name;

    @NotEmpty(message = "Ingredient list can't be empty")
    private List<RecipeIngredientDto> recipeIngredients = new ArrayList<>();

    @DecimalMin(value = "0.00")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal margin;
}