package com.example.inventoryservice.projection;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface UsedIngredientView {
    @Value("#{target.restaurant_id}")
    Long getRestaurantId();

    @Value("#{target.recipe_ingredients_id}")
    Long getRecipeIngredientId();

    @Value("#{target.sum}")
    BigDecimal getSum();
}

