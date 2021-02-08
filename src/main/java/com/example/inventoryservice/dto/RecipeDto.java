package com.example.inventoryservice.dto;

import com.example.inventoryservice.entity.RecipeIngredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDto {

    private Long id;

    @NotBlank(message = "Name can't be empty.")
    private String name;

    private RestaurantDto restaurant;

    @NotEmpty(message = "Ingredient list can't be empty")
    private List<RecipeIngredientDto> recipeIngredients = new ArrayList<>();
}
