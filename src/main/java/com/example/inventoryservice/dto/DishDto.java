package com.example.inventoryservice.dto;

import com.example.inventoryservice.entity.Recipe;
import com.example.inventoryservice.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishDto {

    private Long id;

    private String name;

    private RestaurantDto restaurant;

    private BigDecimal price;

    private RecipeDto recipe;
}
