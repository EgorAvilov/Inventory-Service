package com.example.inventoryservice.dto;

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
public class IngredientDto {

    private Long id;

    private String name;

    private Integer amount;

    private String measureUnit;

    private BigDecimal price;

    private Restaurant restaurant;
}
