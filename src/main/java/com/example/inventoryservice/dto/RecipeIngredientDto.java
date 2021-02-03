package com.example.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeIngredientDto {

    private Long id;

    @DecimalMin(value = "0.0", inclusive = false, message = "Amount can't be <=0")
    private BigDecimal amount;

    private IngredientDto ingredient;
}
