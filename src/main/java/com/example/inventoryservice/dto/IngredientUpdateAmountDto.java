package com.example.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientUpdateAmountDto {

    @NotBlank(message = "Name can't be empty.")
    private String name;

    @DecimalMin(value = "0.00", inclusive = false, message = "Amount can't be <=0")
    @Digits(integer = 15, fraction = 2)
    private BigDecimal amount;
}
