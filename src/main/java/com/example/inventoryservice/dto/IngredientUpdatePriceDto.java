package com.example.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientUpdatePriceDto {

    @NotBlank(message = "Name can't be empty.")
    private String name;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price can't be <=0")
    private BigDecimal price;
}
