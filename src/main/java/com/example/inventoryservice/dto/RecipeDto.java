package com.example.inventoryservice.dto;

import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDto {

    private Long id;

    private String name;

    private RestaurantDto restaurant;

    private List<IngredientDto> recordsList;
}
