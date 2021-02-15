package com.example.inventoryservice.converter;

import com.example.inventoryservice.dto.IngredientDto;
import com.example.inventoryservice.dto.RecipeIngredientDto;
import com.example.inventoryservice.entity.Ingredient;
import com.example.inventoryservice.entity.RecipeIngredient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeIngredientConverter {
    public RecipeIngredientDto entityToDto(RecipeIngredient ingredient) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(ingredient, RecipeIngredientDto.class);
    }

    public RecipeIngredient dtoToEntity(RecipeIngredientDto ingredientDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(ingredientDto, RecipeIngredient.class);
    }

    public List<RecipeIngredientDto> entityToDto(List<RecipeIngredient> ingredients) {
        return ingredients.stream()
                          .map(this::entityToDto)
                          .collect(Collectors.toList());
    }

    public List<RecipeIngredient> dtoToEntity(List<RecipeIngredientDto> ingredientDtos) {
        return ingredientDtos.stream()
                             .map(this::dtoToEntity)
                             .collect(Collectors.toList());
    }
}
