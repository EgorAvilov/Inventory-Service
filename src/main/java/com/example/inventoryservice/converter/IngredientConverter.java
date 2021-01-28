package com.example.inventoryservice.converter;

import com.example.inventoryservice.dto.IngredientDto;
import com.example.inventoryservice.entity.Ingredient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IngredientConverter {
    public IngredientDto entityToDto(Ingredient ingredient) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(ingredient, IngredientDto.class);
    }

    public Ingredient dtoToEntity(IngredientDto ingredientDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(ingredientDto, Ingredient.class);
    }

    public List<IngredientDto> entityToDto(List<Ingredient> ingredients) {
        return ingredients.stream()
                          .map(this::entityToDto)
                          .collect(Collectors.toList());
    }

    public List<Ingredient> dtoToEntity(List<IngredientDto> ingredientDtos) {
        return ingredientDtos.stream()
                             .map(this::dtoToEntity)
                             .collect(Collectors.toList());
    }
}
